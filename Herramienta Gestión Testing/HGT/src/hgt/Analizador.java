/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hgt;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;
import com.github.javaparser.ast.visitor.TreeVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Santi
 */
public class Analizador extends VoidVisitorAdapter<Void> {
    private List<StatsArchivo> statsArchivos;
    private List<StatsMetodo> statsMetodos;
    private Map<String, Integer> metodosPropios;
    
    public Analizador (List<StatsArchivo> statsArchivos, List<StatsMetodo> statsMetodos) {
        this.statsArchivos = statsArchivos;
        this.statsMetodos = statsMetodos;
        metodosPropios = new HashMap<>();
    }
    
    public void analizar () throws FileNotFoundException {
        // ANALISIS DE ARCHIVOS
        for (StatsArchivo statsArchivo : statsArchivos) {
            FileInputStream in = new FileInputStream(statsArchivo.getArchivo());

            // parse
            CompilationUnit cu = JavaParser.parse(in);

            // Visitador de clases (para extraer métodos)
            cu.accept(new VisitadorArchivos(statsArchivos, statsMetodos, statsArchivo, metodosPropios), null);
        }
        
        // ANALISIS EN PROFUNDIDAD
        for (StatsArchivo statsArchivo : statsArchivos) {
            FileInputStream in = new FileInputStream(statsArchivo.getArchivo());

            // parse
            CompilationUnit cu = JavaParser.parse(in);

            // Visitador de clases (para extraer métodos)
            cu.accept(new VisitadorProfundidad(statsMetodos, metodosPropios), null);
        }
        
        // el fan out no queda otra que hacerlo acá
        for (StatsMetodo sm : statsMetodos) {
            String clave = sm.getDefMetod().getNombre();
            int fanOut = 0;
            
            if (metodosPropios.containsKey(clave))
                fanOut = metodosPropios.get(clave);
            sm.setFanOut(fanOut);
        }
    }
    
    private static class VisitadorArchivos extends VoidVisitorAdapter<Void> {
        private List<StatsArchivo> statsArchivos;
        private List<StatsMetodo> statsMetodos;
        private StatsArchivo statsArchivo;
        private Map<String, Integer> metodosPropios;
        
        public VisitadorArchivos (List<StatsArchivo> statsArchivos, List<StatsMetodo> statsMetodos,
                StatsArchivo statsArchivo, Map<String, Integer> metodosPropios) {
            this.statsArchivos = statsArchivos;
            this.statsMetodos = statsMetodos;
            this.statsArchivo = statsArchivo;
            this.metodosPropios = metodosPropios;
        }
        
        @Override
        public void visit(ClassOrInterfaceDeclaration c, Void arg) {
            String clase = c.getName().toString();
            for (MethodDeclaration m : c.getMethods()) {
                List<String> argumentos = new ArrayList<>(m.getParameters().size());
                
                for (Parameter p : m.getParameters()) {
                    argumentos.add(p.getType().toString());
                }
                
                DefinicionMetodo defMetod = new DefinicionMetodo(clase, m.getName().toString(), argumentos);
                StatsMetodo statsMetodo = new StatsMetodo(defMetod);
                statsMetodo.setTexto(m.toString());
                statsArchivo.getMetodos().add(statsMetodo);
                statsMetodos.add(statsMetodo);
                metodosPropios.put(m.getName().toString(), 0);
            }
            
            super.visit(c, arg);
        }
    }
    
    private static class VisitadorProfundidad extends VoidVisitorAdapter<Void> {
        private List<StatsMetodo> statsMetodos;
        private Map<String, Integer> metodosPropios = new HashMap<>();
        
        public VisitadorProfundidad (List<StatsMetodo> statsMetodos,  Map<String, Integer> metodosPropios) {
            this.statsMetodos = statsMetodos;
            this.metodosPropios = metodosPropios;
        }
        
        @Override
        public void visit(ClassOrInterfaceDeclaration c, Void arg) {
            String clase = c.getName().toString();
            for (MethodDeclaration m : c.getMethods()) {
                List<String> argumentos = new ArrayList<>(m.getParameters().size());
                for (Parameter p : m.getParameters()) {
                    argumentos.add(p.getType().toString());
                }
                
                DefinicionMetodo defMetod = new DefinicionMetodo(clase, m.getName().toString(), argumentos);
                // busco la def del metodo en la lista de statsmetodos
                StatsMetodo sm = statsMetodos.get(statsMetodos.indexOf(new StatsMetodo(defMetod)));
                analizar (m, sm);
            }
            super.visit(c, arg);
        }
        
        private void analizar (MethodDeclaration m, StatsMetodo stats) {
            // ********************
            // ** LINEAS TOTALES **
            // ********************
            int lineasTotales = 1;
            //System.out.println(m);
            if(m.getBody().isPresent()) {
                String[] lineas = m.getBody().get().toString().split("\\r?\\n");
                for(String linea : lineas) {
                    //System.out.println(linea);
                    if (StringUtils.isNotEmpty(linea) && StringUtils.isNotBlank(linea))
                        lineasTotales++;
                }
            }
            stats.setLineasTotales(lineasTotales);
            // *****************
            // ** COMENTARIOS **
            // *****************
            int comentariosTotales = 0;
            // JAVADOC
            if(m.getJavadocComment().isPresent()) {
                String[] lineas = m.getJavadocComment().get().toString().split("\\r?\\n");
                for(String linea : lineas) {
                    if (StringUtils.isNotEmpty(linea) && StringUtils.isNotBlank(linea))
                        comentariosTotales++;
                }
            }
            // ORPHANS
            for (Comment c : m.getOrphanComments()) {
                if (c instanceof BlockComment || c instanceof JavadocComment) {
                    String[] lineas = c.toString().split("\\r?\\n");
                    for(String linea : lineas) {
                        if (StringUtils.isNotEmpty(linea) && StringUtils.isNotBlank(linea))
                            comentariosTotales++;
                    }
                } else
                    comentariosTotales++;
            }
            // COMENTARIOS CONTENIDOS
            for (Comment c : m.getAllContainedComments()) {
                if (c instanceof BlockComment || c instanceof JavadocComment) {
                    String[] lineas = c.toString().split("\\r?\\n");
                    for(String linea : lineas) {
                        if (StringUtils.isNotEmpty(linea) && StringUtils.isNotBlank(linea))
                            comentariosTotales++;
                    }
                } else
                    comentariosTotales++;
            }
            stats.setLineasComentarios(comentariosTotales);
            // ******************************
            // ** HALSTEAD, CC y FANIN/OUT **
            // ******************************
            new TreeVisitor() {
                int n1 = 0, n2 = 0, N1 = 0, N2 = 0;
                int cc = 1;
                int fanIn = 0;
                Set<String> operadoresUnicos = new HashSet<>();
                Set<String> operandosUnicos = new HashSet<>();
                StatsMetodo sm = stats;
                
                @Override
                public void process(Node node) {
                    if(node instanceof SimpleName && !(node.getParentNode().get() instanceof FieldAccessExpr)) {
                        operadoresUnicos.add(node.toString());
                        N1++;
                    } else if(node instanceof Type) {
                        operadoresUnicos.add(node.toString());
                        N1++;
                    } else if(node instanceof LiteralExpr && !(node instanceof StringLiteralExpr)) {
                        operandosUnicos.add(node.toString());
                        N2++;
                    } else if (node instanceof BinaryExpr) {
                        operadoresUnicos.add(((BinaryExpr) (node)).getOperator().toString());
                        N1++;
                    } else if (node instanceof UnaryExpr) {
                        operadoresUnicos.add(((UnaryExpr) (node)).getOperator().toString());
                        N1++;
                    } else if (node instanceof ConditionalExpr) {
                        cc++;
                        operadoresUnicos.add("?");
                        N1++;
                    } else if (node instanceof MethodCallExpr) {
                        MethodCallExpr expr = (MethodCallExpr)node;
                        String clave = expr.getName().toString();
                        if(metodosPropios.containsKey(clave)) {
                            int var = metodosPropios.get(clave);
                            metodosPropios.put(clave, var + 1);
                            fanIn++;
                        }
                    } else if (node instanceof IfStmt) {
                        cc++;
                        String condition = ((IfStmt)node).getCondition().toString();
                        cc += StringUtils.countMatches(condition, "||");
                        cc += StringUtils.countMatches(condition, "&&");
                        operadoresUnicos.add(node.getClass().getSimpleName());
                        N1++;
                    } else if (node instanceof WhileStmt) {
                        cc++;
                        String condition = ((WhileStmt)node).getCondition().toString();
                        cc += StringUtils.countMatches(condition, "||");
                        cc += StringUtils.countMatches(condition, "&&");
                        operadoresUnicos.add(node.getClass().getSimpleName());
                        N1++;
                    } else if (node instanceof DoStmt) {
                        cc++;
                        String condition = ((DoStmt)node).getCondition().toString();
                        cc += StringUtils.countMatches(condition, "||");
                        cc += StringUtils.countMatches(condition, "&&");
                        operadoresUnicos.add(node.getClass().getSimpleName());
                        N1++;
                    } else if (node instanceof ForStmt) {
                        cc++;
                        ForStmt stmt = ((ForStmt)node);
                        if (stmt.getCompare().isPresent()) {
                            //System.out.println(stmt.getCompare().get().toString());
                            String condition = stmt.getCompare().get().toString();
                            cc += StringUtils.countMatches(condition, "||");
                            cc += StringUtils.countMatches(condition, "&&");
                        }
                        operadoresUnicos.add(node.getClass().getSimpleName());
                        N1++;
                    } else if (node instanceof SwitchStmt) {
                        SwitchStmt stmt = ((SwitchStmt) node);
                        cc+= stmt.getEntries().size();
                        if (!stmt.getEntries().isEmpty()) {
                            operadoresUnicos.add("case");
                            N1++;
                        }
                        operadoresUnicos.add(node.getClass().getSimpleName());
                        N1++;
                    } else if (node instanceof Statement) {
                        if (!(node instanceof EmptyStmt|| node instanceof ExpressionStmt
                                || node instanceof SwitchEntryStmt|| node instanceof UnparsableStmt)) {
                            operadoresUnicos.add(node.getClass().getSimpleName());
                            N1++;
                        }    
                    }
                }
                
                public void visitar (MethodDeclaration m) {
                    visitPreOrder(m);
                    n1 = operadoresUnicos.size();
                    n2 = operandosUnicos.size();

                    /*for(String s : operadoresUnicos)
                        System.out.println(s);
                    for(String s : operandosUnicos)
                        System.out.println(s);*/
            
                    sm.setComplejidadCiclomatica(cc);
                    Halstead halstead = new Halstead(n1, n2, N1, N2);
                    sm.setHalsteadLong(halstead.getLong());
                    sm.setHalsteadVol(halstead.getVol());
                    sm.setFanIn(fanIn);
                }
            }.visitar(m);
        }
    }
}

