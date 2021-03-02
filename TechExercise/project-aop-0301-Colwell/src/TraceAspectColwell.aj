
public aspect TraceAspectColwell {

	pointcut classToTrace(): within(ComponentApp) 
	|| within(DataApp) || within(ServiceApp);
	
	pointcut methodToTrace(): classToTrace() && execution(String getName());
	
	before(): methodToTrace() {
		
		String info = thisJoinPointStaticPart.getSignature() + ", " 
				+ thisJoinPointStaticPart.getSourceLocation().getLine();
		System.out.println("[BGN] " + info);
		
	}
	
	after(): methodToTrace() {
		System.out.println("[END] " + thisJoinPointStaticPart.getSourceLocation().getFileName());
	}

}
