package test;
import javax.annotation.PostConstruct;


public class TerminatorQuoter implements Quoter{

	String quote;
	
	@RandomQuantity(min= 2, max = 7)	
	private int repeat ;
	
	public void setQuote(String quote) {
		this.quote = quote;
	}

	@PostConstruct
	public void initMethod() {
		
		System.out.println("Phase2");
		System.out.println(repeat);
	}
	
	public TerminatorQuoter() {
		System.out.println("Phase1");
		System.out.println(repeat);
		
	}
	public void sayQuote() {
		for(int i=0; i< repeat ; i++) {
		   System.out.println(quote);
		}
		
	}

}
