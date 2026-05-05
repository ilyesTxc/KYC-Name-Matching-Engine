package kyc.app;

public class Main {
    public static void main(String[] args){
        Configuration config = new Configuration();
        ListManager listManager = new ListManager();
        Menu menu = new Menu(config, listManager);
        menu.afficherMenuPrincipal();
    }
}
