import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TestRegx {

    public static void main(String[] args){
	if(args.length < 2) {
		usage();
		System.exit(1);	
	}
	Pattern pattern = Pattern.compile(args[0]);
	Matcher matcher = pattern.matcher(args[1]);
	//Matcher matcher = pattern.matcher("02/02/2003");
	//Matcher matcher = pattern.matcher("02/02/2003");
            boolean found = false;
            while (matcher.find()) {
                System.out.println("I found the text " + matcher.group() + " starting at " +
                   "index " + matcher.start() + " and ending at index " + matcher.end() );
                found = true;
            }
            if(!found){
                System.out.println("No match found.");
            }
    }
    
    public static void usage() {
    	System.out.println("Usage: java TestRegx <regx-pattern> <string>");
    }
}

