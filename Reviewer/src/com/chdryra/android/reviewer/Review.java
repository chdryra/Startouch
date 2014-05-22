package com.chdryra.android.reviewer;

public interface Review {
	
	//Core data
	public RDId getID();
	
	public RDTitle getTitle();
	public void setTitle(String title);
	
	public RDRating getRating();
	public void setRating(float rating);

	public ReviewTagCollection getTags();
	
	public ReviewNode getReviewNode();
	
	//Optional data
	public void setComments(RDCollection<RDComment> comment);
	public RDCollection<RDComment> getComments();
	public void deleteComments();
	public boolean hasComments();

	public RDImage getImage();
	public void setImage(RDImage image);
	public void deleteImage();
	public boolean hasImage();
	
	public RDLocation getLocation();
	public void setLocation(RDLocation location);
	public void deleteLocation();	
	public boolean hasLocation();

	public RDCollection<RDFact> getFacts();
	public void setFacts(RDCollection<RDFact> facts);
	public void deleteFacts();	
	public boolean hasFacts();
	
	public RDUrl getURL();
	public void setURL(RDUrl url);
	public void deleteURL();	
	public boolean hasURL();
	
	public RDDate getDate();
	public void setDate(RDDate date);
	public void deleteDate();	
	public boolean hasDate();

	public RDProCons getProsCons();
	public void setProsCons(RDProCons prosCons);
	public void deleteProsCons();	
	public boolean hasProsCons();

	//For speed and comparison
	@Override
	public boolean equals(Object o);
	@Override
	public int hashCode();
}
