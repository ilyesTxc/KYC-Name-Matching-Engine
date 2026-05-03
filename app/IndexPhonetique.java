package kyc.app;

import kyc.model.Nom;

import java.util.*;
import java.util.stream.Collectors;

public class IndexPhonetique {

    private static final Map<Character, Character> soundCode;
    private final Map<String, Set<Nom>> index;
    private final int lengthofSanctions;

    public Set<Nom> getBucket(String code) {
        return index.getOrDefault(code, Collections.emptySet());
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
                    idx.computeIfAbsent(code, k->new LinkedHashSet<>()).add(n);
                }
            }
        }

        this.index =Collections.unmodifiableMap(idx);

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
        StringBuilder digits =new StringBuilder();

        char previousCode =soundCode.getOrDefault(firstLetter, '0');

        for (int i = 1; ( i <clean.length() && digits.length() < 3 ); i++) {

            char letter =clean.charAt(i);
            char code =soundCode.getOrDefault(letter, '0');

            if (code != '0' && code != previousCode){
                digits.append(code);
            }

            if (code != '0'){
                previousCode = code;
            }
        }

        while (digits.length() < 3) {
            digits.append('0');
        }

        return firstLetter + digits.toString();
    }


    public static List<String> encoderTokens(Nom nom){
        if (nom == null){
            return List.of();
        }

        List<String> tokens = nom.getNomPretraite();

        if (tokens == null || tokens.isEmpty()){
            return List.of();
        }

        return tokens.stream().map(IndexPhonetique::soundexToken).collect(Collectors.toList());
    }
}