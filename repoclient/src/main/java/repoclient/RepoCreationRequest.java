package repoclient;

public class RepoCreationRequest {
	
	public RepoCreationRequest(String name, String scmId, boolean forkable) {
		super();
		this.name = name;
		this.scmId = scmId;
		this.forkable = forkable;
	}
	String name;
	@Override
	public String toString() {
		return "RepoCreationRequest [name=" + name + ", scmId=" + scmId + ", forkable=" + forkable + "]";
	}
	String scmId;
	boolean forkable;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getScmId() {
		return scmId;
	}
	public void setScmId(String scmId) {
		this.scmId = scmId;
	}
	public boolean isForkable() {
		return forkable;
	}
	public void setForkable(boolean forkable) {
		this.forkable = forkable;
	}

}
