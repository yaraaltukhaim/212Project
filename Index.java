public class Index {

    // Represents a document and its words
    class Document {
        LinkedList<String> index; 
        int documentID;

        public Document() {
            documentID = 0;
            index = new LinkedList<>();
        }

        public void insertNew(String W) {
            index.insert(W);
        }

        public boolean wordExists(String word) {
            if (index.empty()) return false;

            index.findFirst();
            for (int i = 0; i < index.size; i++) {
                if (index.retrieve().compareTo(word) == 0) return true;
                index.findNext();
            }
            return false;
        }
    }

    // Array to store documents
    Document[] documents;

    public Index() {
        documents = new Document[50];
        for (int i = 0; i < documents.length; i++) {
            documents[i] = new Document();
            documents[i].documentID = i;
        }
    }

    public void addtoDocument(int dID, String W) {
        if (dID >= 0 && dID < documents.length) {
            documents[dID].insertNew(W);
        } else {
            System.out.println("Invalid document ID: " + dID);
        }
    }

    public void printDocument(int dID) {
        if (documents[dID].index.empty()) {
            System.out.println("Empty Document");
            return;
        }

        documents[dID].index.findFirst();
        for (int i = 0; i < documents[dID].index.size; i++) {
            System.out.print(documents[dID].index.retrieve() + " ");
            documents[dID].index.findNext();
        }
        System.out.println();
    }

    public boolean[] findDocuments(String W) {
        boolean[] flag = new boolean[documents.length];
        for (int i = 0; i < documents.length; i++) {
            flag[i] = documents[i].wordExists(W);
        }
        return flag;
    }

    public boolean[] AND_OR_Function(String W) {
        if (!W.contains(" OR ") && !W.contains(" AND ")) {
            return findDocuments(W.toLowerCase().trim());
        }

        if (W.contains(" OR ") && W.contains(" AND ")) {
            String[] AND_ORs = W.split(" OR ");
            boolean[] result = AND_Function(AND_ORs[0]);

            for (int i = 1; i < AND_ORs.length; i++) {
                boolean[] tempResult = AND_Function(AND_ORs[i]);
                for (int j = 0; j < documents.length; j++) {
                    result[j] = result[j] || tempResult[j];
                }
            }
            return result;
        }

        if (W.contains(" AND ")) {
            return AND_Function(W);
        }

        return OR_Function(W);
    }

    public boolean[] OR_Function(String W) {
        String[] orParts = W.split(" OR ");
        boolean[] result = findDocuments(orParts[0].toLowerCase().trim());

        for (int i = 1; i < orParts.length; i++) {
            boolean[] tempResult = findDocuments(orParts[i].toLowerCase().trim());
            for (int j = 0; j < documents.length; j++) {
                result[j] = result[j] || tempResult[j];
            }
        }
        return result;
    }

    public boolean[] AND_Function(String W) {
        String[] andParts = W.split(" AND ");
        boolean[] result = findDocuments(andParts[0].toLowerCase().trim());

        for (int i = 1; i < andParts.length; i++) {
            boolean[] tempResult = findDocuments(andParts[i].toLowerCase().trim());
            for (int j = 0; j < documents.length; j++) {
                result[j] = result[j] && tempResult[j];
            }
        }
        return result;
    }
}
