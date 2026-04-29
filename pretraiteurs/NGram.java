package kyc.pretraiteurs;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Nodes.collect

import static java.util.stream.Nodes.collect;()

public class NGram implements PreTraiteurNom{
    private int n;
    public NGram(){
        this.n=2;
    }
    public NGram(int n){
        this.n=n;
    }
    public List<String> preTraiter(List<String> noms){
        return  noms.stream().flatMap(noms → extraireNGrams(noms).stream()).collect(Collectors.toList());
    }
    private List<String> extraireNGrams(String s){
        if (s == null || s.length() < n) return Collections.emptyList();
        return IntStream.rangeClosed(0, s.length() - n).mapToObj(int i → s.substring(i, i+n)).collect(Collectors.toList());
    }
}


