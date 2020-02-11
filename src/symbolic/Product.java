package symbolic;

import java.util.*;

public class Product extends Expression {

    private final Expression [] terms;

    public Product(Expression [] a) {
	terms = new Expression[a.length];
	for (int j=0;j<a.length;j++)
	    terms[j]=a[j];
    }

    public Product(Expression a, Expression b) {
	terms = new Expression [] {a, b};
    }

    public String [] getVariables() { 
	int t=0;
	for (int j=0;j<terms.length;j++)
	    t+=terms[j].getVariables().length;
	String [] l=new String [t];
	int c=0;
	for (int j=0;j<terms.length;j++) {
	    String [] tt=terms[j].getVariables();
	    for (int k=0;k<tt.length;k++)
		l[c++]=tt[k];
	}
	return l;
    }

    public String toString() { 
	String r = "";
	for (int j=0;j<terms.length;j++) {
	    if (j>0) r+="*";
	    Expression f=terms[j];
	    if (f.getPriority()<getPriority()) r+="("+f+")";
	    else  r+=""+f;
	}
	return r;
    }

    public Expression differentiate(String x) {
	Expression [] s = new Expression[terms.length];
	Expression [] p = new Expression[terms.length];
	for (int j=0;j<terms.length;j++) {
	    for (int k=0;k<terms.length;k++) {
		if (j==k) p[k]=terms[k].differentiate(x);
		else p[k]=terms[k];
	    }
	    s[j]=new Product(p);
	}
	return new Sum(s);
    }

    public double evaluate(String [] vs, double [] xs) {
	double p=1.;
	for (int j=0;j<terms.length;j++)
	    p=p*terms[j].evaluate(vs, xs);
	return p;
    }

    public Expression substitute(String v, Expression e) {
	Expression [] p=new Expression [terms.length];
	for (int j=0;j<terms.length;j++) 
	    p[j]=terms[j].substitute(v, e);
	return new Product(p);
    }

    protected int getPriority() {return 10;}

}


