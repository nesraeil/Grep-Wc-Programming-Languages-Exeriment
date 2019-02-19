package yu.JavaOO;

public class Main {
    /**
     * wc C:\Users\PC\Documents\YU_Git\NathanielEsraeilian\PL\FinalProject\test.txt
     * grep this C:\Users\PC\Documents\YU_Git\NathanielEsraeilian\PL\FinalProject\test.txt
     * grep this C:\Users\PC\Documents\YU_Git\NathanielEsraeilian\PL\FinalProject\test.txt | wc
     */
    public static void main(String[] args) {
        GrepAndWc newApp = new GrepAndWc(args);
    }
}
