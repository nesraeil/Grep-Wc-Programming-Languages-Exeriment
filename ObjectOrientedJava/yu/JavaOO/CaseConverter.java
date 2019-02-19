package yu.JavaOO;

public class CaseConverter implements CaseConvert {
    @Override
    public String process(String input) {
        return input.toLowerCase();
    }
}
