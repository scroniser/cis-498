/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plutosched;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Matt
 */
public class classScheduler {
    private ArrayList<professor> professorArray;
    private ArrayList<room> roomArray;
    private ArrayList<sections>sectionArray;
    private ArrayList<sections> unscheduledSectionArray=new ArrayList();
    private ArrayList<sections> scheduledClasses=new ArrayList();
    private ArrayList<professor> unschedProfs=new ArrayList();
    private Double[][] travelMatrix;
    private static databaseFunctions logs=new databaseFunctions();
    public classScheduler(ArrayList<professor> professorArray,ArrayList<room> roomArray, ArrayList<sections> sectionArray,Double[][] travelMatrix){
        //this is the class the loops through the array list of sections and trys to schedule them
        classScheduler.logs.logEvent("Starting Making Schedule");
        this.professorArray=professorArray;
        this.roomArray=roomArray;
        this.sectionArray=sectionArray;
        this.travelMatrix=travelMatrix;
        removeProfs();
        
        
        for (int i=0; i<this.sectionArray.size();i++){
            
            int[] availableProfAndRoom=profAndRoomIndex(this.sectionArray.get(i));
            if(availableProfAndRoom[0]==-1){
                this.unscheduledSectionArray.add(this.sectionArray.get(i));
            }
            else{
                //add to schedule prof and room objects
                int prof=availableProfAndRoom[0];
                int room=availableProfAndRoom[1];
                //add the section to the professor
                this.professorArray.get(prof).addSection();
                //add instructors name to section
                this.sectionArray.get(i).setInstructor(this.professorArray.get(prof).getInstructorName());
                //add the campus the prof is on
                //this.professorArray.get(prof).setOnCampus(this.roomArray.get(room).getCampusIndex());
                //add room number to section
                this.sectionArray.get(i).setRoom(this.roomArray.get(room).getRoom());
                //add building to section
                this.sectionArray.get(i).setBuilding(this.roomArray.get(room).getBuilding());                
                switch(this.sectionArray.get(i).getDays()){
                 case 101000:
                     //add the campus the prof is on                     
                     this.professorArray.get(prof).setOnTuesdayCampus(this.roomArray.get(room).getCampusIndex());
                     this.professorArray.get(prof).setOnThursdayCampus(this.roomArray.get(room).getCampusIndex());
                     this.professorArray.get(prof).setTuesdayEnd(this.sectionArray.get(i).getEndTime());
                     this.professorArray.get(prof).setThursdayEnd(this.sectionArray.get(i).getEndTime());
                     this.roomArray.get(room).setTuesdayEnd(this.sectionArray.get(i).getEndTime());
                     this.roomArray.get(room).setThursdayEnd(this.sectionArray.get(i).getEndTime());
                     break;
                 case 10:                     
                     this.professorArray.get(prof).setOnSaturdayCampus(this.roomArray.get(room).getCampusIndex());
                     this.professorArray.get(prof).setSaturdayEnd(this.sectionArray.get(i).getEndTime());
                     this.roomArray.get(room).setSaturdayEnd(this.sectionArray.get(i).getEndTime());
                     break;
                 case 1010100:                     
                     this.professorArray.get(prof).setOnMondayCampus(this.roomArray.get(room).getCampusIndex());
                     this.professorArray.get(prof).setOnWednesdayCampus(this.roomArray.get(room).getCampusIndex());
                     this.professorArray.get(prof).setOnFridayCampus(this.roomArray.get(room).getCampusIndex());
                     this.professorArray.get(prof).setMondayEnd(this.sectionArray.get(i).getEndTime());
                     this.professorArray.get(prof).setWednesdayEnd(this.sectionArray.get(i).getEndTime());
                     this.professorArray.get(prof).setFridayEnd(this.sectionArray.get(i).getEndTime());
                     this.roomArray.get(room).setMondayEnd(this.sectionArray.get(i).getEndTime());
                     this.roomArray.get(room).setWednesdayEnd(this.sectionArray.get(i).getEndTime());
                     this.roomArray.get(room).setFridayEnd(this.sectionArray.get(i).getEndTime());
                     break;
                 case 1010000:
                     this.professorArray.get(prof).setOnMondayCampus(this.roomArray.get(room).getCampusIndex());
                     this.professorArray.get(prof).setOnWednesdayCampus(this.roomArray.get(room).getCampusIndex());
                     this.professorArray.get(prof).setMondayEnd(this.sectionArray.get(i).getEndTime());
                     this.professorArray.get(prof).setWednesdayEnd(this.sectionArray.get(i).getEndTime());
                     this.roomArray.get(room).setMondayEnd(this.sectionArray.get(i).getEndTime());
                     this.roomArray.get(room).setWednesdayEnd(this.sectionArray.get(i).getEndTime());
                     break;
                 default: 
                     break;
                    //something is wrong if we get here
                }//end switch
                
                this.sectionArray.get(i).insertSqlSched();
                this.scheduledClasses.add(this.sectionArray.get(i));
            }//end else
        }//end i for
        
        classScheduler.logs.logEvent("Finished Making Schedule");
    }
    
    private void removeProfs(){
        //remove unavailable professors to increase performance
        for(int i=0; i<this.professorArray.size();i++){
            if(this.professorArray.get(i).canTeachClasses()==false){
                this.unschedProfs.add(this.professorArray.get(i));
                this.professorArray.remove(i);
            }
        }
    }
    
    private int[] profAndRoomIndex(sections sectionToSched){
       //loops through rooms and professors and trys to find a match for the schedule.  This is the main algorithm
        
        int profAndRoom[]=new int[2];
        for (int x=0; x<this.roomArray.size();x++){ 
            //find a room that can fit the class
            if(roomArray.get(x).getCapacity()>=sectionToSched.getSeatsNeeded()){
                //see if media is needed and available
                
                if(mediaMatch(sectionToSched.getMediaRequired().toUpperCase(),roomArray.get(x))==true){
                    //see if the room is available by checking if the times work for the room 
                    
                    if(roomAvailable(roomArray.get(x),sectionToSched)==true){
                        
                        //if a room is available see if we can find a professor to teach the class in the room                        
                        for(int i=0;i<this.professorArray.size();i++){
                            //check to see if this professor can teach 
                        
                            if(this.professorArray.get(i).canTeachClasses()==true){
                                
                                //check to see if they can teach the in the sections department
                                if(sectionToSched.getDepartment().equals(professorArray.get(i).getDepartment())){
                                    //check to see if the professor will teach in the room on the campus
                                    if(Arrays.asList(professorArray.get(i).getNorthCampus(),professorArray.get(i).getSouthCampus(),professorArray.get(i).getEastCampus(),professorArray.get(i).getWestCampus()).contains(roomArray.get(x).getCampus())){
                                        //check to see if the prof is available on those days if so return index of room and prof
                                       
                                        //System.out.println("campus index before"+roomArray.get(x).getCampusIndex());
                                        if(profAvailable(professorArray.get(i),sectionToSched,roomArray.get(x).getCampusIndex())==true){
                                           profAndRoom[0]=i;
                                           profAndRoom[1]=x; 
                                           
                                           return profAndRoom;
                                        }




                                    }//end will teach on campus if 
                                }//end department if
                           }//end can teach if
                        }//end prof for
                    }//end room available if
                }//end media available if
            }//end room capacity if
        }//end room for
        profAndRoom[0]=-1;
        profAndRoom[1]=-1;
        
        return profAndRoom;
    }
    
    
    private boolean profAvailable(professor prof, sections sect, int roomCampusIndex){
      //finds if a professor is avaible at the given time for the given campus  
            
            int addTime;
//            int profPlace=prof.getOnCampus();             
//             if(profPlace==-1){
//                 addTime=0;
//             }
//             else{              
//                 double travelTime= travelMatrix[profPlace][roomCampusIndex]*100;                  
//                 addTime=(int) travelTime;                 
//             }
            
            switch(sect.getDays()){
                 case 101000:
                    int profPlaceTuesday=prof.getOnTuesdayCampus();
                    int profPlaceThursday=prof.getOnThursdayCampus();
                    if(profPlaceTuesday==-1 && profPlaceThursday==-1){
                        addTime=0;
                    }
                    else if(profPlaceTuesday==-1){
                        double travelTimeThursday= travelMatrix[profPlaceThursday][roomCampusIndex]*100;
                        addTime=(int) travelTimeThursday;
                    }
                    else if(profPlaceThursday==-1){
                        double travelTimeTuesday= travelMatrix[profPlaceTuesday][roomCampusIndex]*100;
                        addTime=(int) travelTimeTuesday;
                    }
                    else{              
                    double travelTimeTuesday= travelMatrix[profPlaceTuesday][roomCampusIndex]*100;
                    double travelTimeThursday= travelMatrix[profPlaceThursday][roomCampusIndex]*100;
                        if(travelTimeTuesday>=travelTimeThursday){
                           addTime=(int) travelTimeTuesday; 
                        }
                        else{
                            addTime=(int) travelTimeThursday;
                        }
                    } 
                     if((prof.getTuesdayEnd()+addTime)<=sect.getStartTime()&&(prof.getThursday()+addTime)<=sect.getStartTime()){
                         return true;
                     }else 
                         return false;
                 case 10:
                     
                     if("YES".equals(prof.getWeekend())){                         
                         int profPlaceSaturday=prof.getOnSaturdayCampus();                         
                         if(profPlaceSaturday==-1){
                             addTime=0;                             
                         }
                         else{
                             double travelTimeSaturday=travelMatrix[profPlaceSaturday][roomCampusIndex]*100;
                             addTime = (int) travelTimeSaturday;                             
                         }
                         if((prof.getSaturday()+addTime)<=sect.getStartTime()){
                             return true;
                         }else{
                             return false;
                         }
                     }else
                         return false;
                 case 1010100:
                     int profPlaceMonday=prof.getOnMondayCampus();
                     int profPlaceWednesday=prof.getOnWednesdayCampus();
                     int profPlaceFriday=prof.getOnFridayCampus();
                     if(profPlaceMonday==-1 && profPlaceWednesday==-1 && profPlaceFriday==-1){
                         addTime=0;
                     }else if(profPlaceMonday==-1 && profPlaceWednesday == -1){
                         double travelTimeFriday=travelMatrix[profPlaceFriday][roomCampusIndex]*100;
                         addTime=(int) travelTimeFriday;                         
                     }else if(profPlaceMonday==-1 && profPlaceFriday == -1){
                         double travelTimeWednesday=travelMatrix[profPlaceWednesday][roomCampusIndex]*100;
                         addTime=(int) travelTimeWednesday;
                     }else if(profPlaceFriday==-1 && profPlaceWednesday==-1){
                         double travelTimeMonday=travelMatrix[profPlaceMonday][roomCampusIndex]*100;
                         addTime=(int) travelTimeMonday;
                     }else if(profPlaceMonday==-1){
                         double travelTimeWednesday=travelMatrix[profPlaceWednesday][roomCampusIndex]*100;
                         double travelTimeFriday=travelMatrix[profPlaceFriday][roomCampusIndex]*100;
                         if(travelTimeWednesday>=travelTimeFriday){
                             addTime=(int) travelTimeWednesday;
                         }
                         else{
                             addTime=(int)travelTimeFriday;
                         }
                     }else if(profPlaceWednesday==-1){
                         double travelTimeMonday=travelMatrix[profPlaceMonday][roomCampusIndex]*100;
                         double travelTimeFriday=travelMatrix[profPlaceFriday][roomCampusIndex]*100;
                         if(travelTimeMonday>=travelTimeFriday){
                             addTime=(int) travelTimeMonday;
                         }
                         else{
                             addTime=(int) travelTimeFriday;
                         }
                     }else if(profPlaceFriday==-1){
                         double travelTimeMonday=travelMatrix[profPlaceMonday][roomCampusIndex]*100;
                         double travelTimeWednesday=travelMatrix[profPlaceWednesday][roomCampusIndex]*100;
                         if(travelTimeMonday>=travelTimeWednesday){
                             addTime=(int) travelTimeMonday;
                         }
                         else{
                             addTime=(int) travelTimeWednesday;
                         }
                     }else{
                         
                         double travelTimeMonday=travelMatrix[profPlaceMonday][roomCampusIndex]*100;
                         double travelTimeWednesday=travelMatrix[profPlaceWednesday][roomCampusIndex]*100;
                         double travelTimeFriday=travelMatrix[profPlaceFriday][roomCampusIndex]*100;
                     
                        if(travelTimeMonday>=travelTimeWednesday && travelTimeMonday>=travelTimeFriday){
                            addTime=(int) travelTimeMonday;
                        }
                        else if(travelTimeWednesday>=travelTimeMonday && travelTimeWednesday>=travelTimeFriday){
                            addTime=(int) travelTimeWednesday;
                        }
                        else{
                            addTime=(int) travelTimeFriday;
                        }
                        
                     }//end else
                     
                     if((prof.getMondayEnd()+addTime)<=sect.getStartTime()&&(prof.getWednesdayEnd()+addTime)<sect.getStartTime()&&(prof.getFriday()+addTime)<sect.getStartTime()){
                         return true;
                     }else
                         return false;
                     
                 case 1010000:
                     int profPlaceMondayTwo=prof.getOnMondayCampus();
                     int profPlaceWednesdayTwo=prof.getOnWednesdayCampus();
                     if(profPlaceMondayTwo==-1 &&profPlaceWednesdayTwo==-1){
                         addTime=0;
                     }
                     else if(profPlaceMondayTwo==-1){
                         double travelTimeWednesdayTwo=travelMatrix[profPlaceWednesdayTwo][roomCampusIndex]*100;
                         addTime=(int) travelTimeWednesdayTwo;
                     }
                     else if(profPlaceWednesdayTwo==-1){
                         double travelTimeMondayTwo=travelMatrix[profPlaceMondayTwo][roomCampusIndex]*100;
                         addTime=(int) travelTimeMondayTwo;
                     }
                     else{
                         double travelTimeMondayTwo=travelMatrix[profPlaceMondayTwo][roomCampusIndex]*100;
                         double travelTimeWednesdayTwo=travelMatrix[profPlaceWednesdayTwo][roomCampusIndex]*100;
                         if(travelTimeMondayTwo>=travelTimeWednesdayTwo){
                             addTime=(int) travelTimeMondayTwo;
                         }
                         else{
                             addTime=(int) travelTimeWednesdayTwo;
                         }
                     }
                     
                     if((prof.getMondayEnd()+addTime)<=sect.getStartTime()&&(prof.getWednesdayEnd()+addTime)<=sect.getStartTime()){
                         return true;
                     }else
                         return false;
                 default: 
                     return false;
             }
        
    }
    
    private boolean roomAvailable(room rm, sections sect){
        //finds if room is available for the given schedule
        boolean available;
       
       
            switch(sect.getDays()){
                 case 101000:
                     if(rm.getTuesdayEnd()<=sect.getStartTime()&&rm.getThursday()<sect.getStartTime()){
                       return available = true;
                     }else 
                         return available=false;
                 case 10:
                     
                     if(rm.getSaturday()<=sect.getStartTime()){
                        
                         return available=true;
                     }else 
                         return available=false;
                 case 1010100:
                     if(rm.getMondayEnd()<=sect.getStartTime()&&rm.getWednesdayEnd()<=sect.getStartTime()&&rm.getFriday()<=sect.getStartTime()){
                         return available=true;
                     }else 
                         return available=false;
                 case 1010000:
                     if(rm.getMondayEnd()<=sect.getStartTime()&&rm.getWednesdayEnd()<=sect.getStartTime()){
                         return available=true;
                     }else 
                         return available=false;
                 default: 
                    return available=false;
             }// switch end
        
    }
    private boolean mediaMatch(String mediaRequired, room mediaRoom){
        //finds if media is required and if so is it available in desired room
        switch (mediaRequired) {
            case "YES":
                if(mediaRoom.isMediaAvailable()==true){
                    return true;
                }else{
                    return false;
                }
            case "NO":
                return true;
            default:
                return true;
        }
        
  
    }
    public ArrayList<professor> getUnschedProfs(){
        return this.unschedProfs;
    }
    
    public ArrayList<sections> getUncedSections(){
        return this.unscheduledSectionArray;
    }
    public ArrayList<sections> getScheduledClasses(){
        return this.scheduledClasses;
    }
    

}
