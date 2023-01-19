import java.io.IOException;
import java.net.URI;
import java.util.HashSet;

class Handler implements URLHandler {
    HashSet<String> strings = new HashSet();

    public String handleRequest(URI url) {
        try {
            if (url.getPath().equals("/add")) {
                String query = getQuery(url);
                if (strings.contains(query)) {
                    return String.format("'%s' already added!", query);
                } else {
                    strings.add(query);
                    return String.format("'%s' added!", query);
                }
            } else if (url.getPath().equals("/search")) {
                String query = getQuery(url);
                String results = "";
                for (String s : strings) {
                    if (s.indexOf(query) != -1)
                        results += String.format("%s\n", s);
                }

                return results;
            } else {
                throw new IllegalArgumentException("Invalid path");
            }
        } catch(Exception e) {
            return "404 Not Found!";
        }
        
    }

    private String getQuery(URI url) {
        String[] parameters = url.getQuery().split("=");
        if (parameters[0].equals("s")) {
            if (parameters.length < 2) {
                return "";
            }
            return parameters[1];
        }

        throw new IllegalArgumentException("Invalid query");
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}