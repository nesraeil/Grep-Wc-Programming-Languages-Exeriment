package yu.JavaOO;

public class LineFilterer implements LineFilter {
    private String filterBy;

    public LineFilterer(String filterBy) {
        this.filterBy = filterBy;
    }
    @Override
    public String process(String input) {
        String[] line = input.split("\n");
        String result = "";
        for(String s:line) {
            if(s.contains(filterBy)) {
                result += s;
                result += "\n";
            }
        }
        return result;
    }
}
