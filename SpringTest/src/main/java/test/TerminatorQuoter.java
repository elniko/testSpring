package test;
import javax.annotation.PostConstruct;

@Profiling
public class TerminatorQuoter implements Quoter{

	String quote;
	
	@RandomQuantity(min= 2, max = 7)	
	private int rep ;
	
	public void setQuote(String quote) {
		this.quote = quote;
	}

	@PostConstruct
	public void initMethod() {
		
		System.out.println("Phase2");
		System.out.println(rep);
	}
	
	public TerminatorQuoter() {
		System.out.println("Phase1");
		
		
	}
	public void sayQuote() {
		System.out.println(rep);
		for(int i=0; i< rep ; i++) {
		   System.out.println(quote);
		}
		
	}

}
