package com.tinkerpop.gremlin.statements;

import com.tinkerpop.blueprints.pgm.Edge;
import com.tinkerpop.blueprints.pgm.Vertex;
import com.tinkerpop.gremlin.paths.Path;
import com.tinkerpop.gremlin.paths.PathLibrary;
import com.tinkerpop.gremlin.paths.Paths;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class TestPaths extends TestCase implements Paths {

    PathLibrary library = new PathLibrary();

    public TestPaths() {
        this.addPath(new TestPathOne());
    }

    public void addPath(Path path) {
        library.addPath(path);
    }

    public void addPaths(Paths paths) {
        library.addPaths(paths);
    }

    public Set<String> getPathNames() {
        return library.getPathNames();
    }

    public Path getPath(String name) {
        return library.getPath(name);
    }

    private class TestPathOne implements Path {
        public String getName() {
            return "test-path-1";
        }

        public Object invoke(Object root) {
            return "undercover cop";
        }
    }

    public void testTrue() {
        assertTrue(true);
    }


    public class CoDeveloperPath implements Path {
        public Object invoke(Object root) {
            if (null != root && root instanceof Vertex) {
                Vertex vertex = (Vertex) root;
                Set<Vertex> projects = new HashSet<Vertex>();
                for (Edge edge : vertex.getOutEdges()) {
                    if (edge.getLabel().equals("created")) {
                        projects.add(edge.getInVertex());
                    }
                }
                Set<Vertex> coDevelopers = new HashSet<Vertex>();
                for (Vertex project : projects) {
                    for (Edge edge : project.getInEdges()) {
                        if (edge.getLabel().equals("created") && edge.getOutVertex() != root) {
                            coDevelopers.add(edge.getOutVertex());
                        }
                    }
                }
                return new ArrayList<Vertex>(coDevelopers);
            } else {
                return null;
            }
        }

        public String getName() {
            return "co-developer";
        }
    }
}
