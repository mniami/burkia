package pl.guideme.data.metadatas;

public class RevisionHelper {
    public static boolean isServerRevisionHigher(String localRevision, String serverRevision) {
        try {
            int localNumber = Integer.parseInt(localRevision);
            int serverNumber = Integer.parseInt(serverRevision);

            return serverNumber > localNumber;
        }
        catch (NumberFormatException nfe){
            return true;
        }
    }
}
