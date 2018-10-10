enum accountType{
    CHECKINGS(25,10),
    SAVINGS(50,20);
	
	private int opeingFee, overDraftFee;
 
    private accountType(int opeingFee, int overDraftFee) {
		this.opeingFee = opeingFee;
		this.overDraftFee = overDraftFee;
	}

     public int getOpeingFee(){return this.opeingFee;}

     public int getOverDraftFee(){return this.overDraftFee;}
}