
class Util {
    public static int indexOf(String src, String tgt) {
        int indexAcc = 0;
        return indexOf(src, tgt, indexAcc);
    }

    public static int indexOf(String src, String tgt, int indexAcc) {
        int lastIndex = src.length() - 1;
        int tgtLength = tgt.length();

        /* Explanation: We got into the end of the string
            and did not find a match between the two provided strings */
        if (lastIndex == -1 || tgtLength > src.length()) {
            return -1;
        }

        // A piece of the src substring with the same length as the tgt
        String pieceOfSrcString = src.substring(0, tgtLength);
        if (pieceOfSrcString.equals(tgt)) {
            return indexAcc;
        }

        indexAcc++;
        return indexOf(src.substring(1), tgt, indexAcc);
    }
}