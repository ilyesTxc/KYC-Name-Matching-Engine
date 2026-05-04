package kyc.app;

import kyc.model.Nom;

import java.util.*;


public class IndexPhonetique {

    private static final Map<Character, Character> soundCode;
    private final Map<String, Set<Nom>> index;
    private final int lengthofSanctions;

    public Set<Nom> getBucket(String code) {

        if(index.containsKey(code)){
            return index.get(code);
        }else{
            return Collections.emptySet();
        }
    }

    public int lengthofSanctions() {
        return lengthofSanctions;
    }

    static {
        soundCode = new HashMap<>();
        for (char c : "BFPV".toCharArray())     soundCode.put(c, '1');
        for (char c : "CGJKQSXZ".toCharArray()) soundCode.put(c, '2');
        for (char c : "DT".toCharArray())        soundCode.put(c, '3');
        soundCode.put('L', '4');
        for (char c : "MN".toCharArray())        soundCode.put(c, '5');
        soundCode.put('R', '6');
        for (char c : "AEIOU".toCharArray())     soundCode.put(c, '0');
        for (char c : "HWY".toCharArray())       soundCode.put(c, '0');
    }

    public IndexPhonetique(List<Nom> sanctions){
        Map<String, Set<Nom>> idx =new HashMap<>();

        if (sanctions != null) {
            for (Nom n : sanctions) {
                for (String code : encoderTokens(n)) {

                    if (!idx.containsKey(code)) {
                        idx.put(code, new LinkedHashSet<>());
                    }

                    idx.get(code).add(n);
                }
            }
        }

        this.index =idx;

        if(sanctions == null ){
            this.lengthofSanctions = 0;
        }else{
            this.lengthofSanctions = sanctions.size();
        }
    }

    public static String soundexToken(String token){
        if (token == null || token.isBlank()){
            return "0000";
        }

        String clean =token.toUpperCase().replaceAll("[^A-Z]", "");

        if (clean.isEmpty()) {
            return "0000";
        }

        char firstLetter =clean.charAt(0);
        String digits ="";

        char previousCode;

        if (soundCode.containsKey(firstLetter)) {
            previousCode = soundCode.get(firstLetter);
        } else {
            previousCode = '0';
        }

        for (int i = 1; ( i <clean.length() && digits.length() < 3 ); i++) {

            char letter =clean.charAt(i);
            char code =soundCode.getOrDefault(letter, '0');

            if (code != '0' && code != previousCode){
                digits+=code;
            }

            if (code != '0'){
                previousCode = code;
            }
        }

        while (digits.length() < 3) {
            digits+='0';
        }

        return firstLetter + digits;
    }


    public static List<String> encoderTokens(Nom nom){
        if (nom == null){
            return List.of();
        }

        List<String> tokens = nom.getNomPretraite();

        if (tokens == null || tokens.isEmpty()){
            return List.of();
        }

        List<String> codes = new ArrayList<>();

        for (String token : tokens) {
            String code = IndexPhonetique.soundexToken(token);
            codes.add(code);
        }

        return codes;
    }
}
