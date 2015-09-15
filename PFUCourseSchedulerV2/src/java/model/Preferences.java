package model;

import java.util.Objects;

public class Preferences {

	private Long userid;
        private String department;
	private String instructor;
	private String sections;
        private String northcampus;
        private String southcampus;
        private String eastcampus;
        private String westcampus;
        private String weekend;

        public Long getUserid() {
                return userid;
        }
        public void setUserid(Long userid) {
            this.userid = userid;
        }
        public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getSections() {
		return sections;
	}
	public void setSections(String sections) {
		this.sections = sections;
	}
	public String getNorthcampus() {
		return northcampus;
	}
	public void setNorthcampus(String northcampus) {
		this.northcampus = northcampus;
	}
        public String getSouthcampus() {
		return southcampus;
	}
	public void setSouthcampus(String southcampus) {
		this.southcampus = southcampus;
	}
        public String getEastcampus() {
		return eastcampus;
	}
	public void setEastcampus(String estcampus) {
		this.eastcampus = estcampus;
	}
        public String getWestcampus() {
		return westcampus;
	}
	public void setWestcampus(String westcampus) {
		this.westcampus = westcampus;
	}
        public String getWeekend() {
		return westcampus;
	}
	public void setWeekend(String weekend) {
		this.weekend = weekend;
	}
        
	@Override
	public String toString() {
		return "Preference [userid=" + userid + "department=" + department + ", instructor=" + instructor
				+ ", sections=" + sections + ", northcampus="
				+ northcampus + ", southcampus="
				+ southcampus + ", eastcampus="
				+ eastcampus + ", westcampus="
				+ westcampus + ", weekend="
				+ weekend + "]";
	}

    @Override
    public boolean equals(Object o) {
        return (this.userid.equals(((Preferences) o).userid) 
                && this.department.equals(((Preferences) o).department)
                && this.instructor.equals(((Preferences) o).instructor)
                && this.sections.equals(((Preferences) o).sections)
                && this.northcampus.equals(((Preferences) o).northcampus)
                && this.southcampus.equals(((Preferences) o).southcampus)
                && this.eastcampus.equals(((Preferences) o).eastcampus)
                && this.westcampus.equals(((Preferences) o).westcampus)
                && this.weekend.equals(((Preferences) o).weekend));
    }
}
