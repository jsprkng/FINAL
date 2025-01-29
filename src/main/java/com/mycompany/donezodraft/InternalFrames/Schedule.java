
package com.mycompany.donezodraft.InternalFrames;

    class Schedule {
        private String day; // e.g., "Mon", "Tue", etc., or "Everyday"
        private int startTime; // Start time in 24-hour format (e.g., 1300 for 1:00 PM)
        private int endTime;   // End time in 24-hour format
        private String reason; // Reason for the schedule
        private String frequency; // Frequency: "once", "weekly", "everyday"


        public Schedule(String day, int startTime, int endTime, String reason, String frequency) {
            if (frequency.equalsIgnoreCase("everyday")) {
                this.day = "Everyday";
            } else {
                this.day = day;
            }
            this.startTime = startTime;
            this.endTime = endTime;
            this.reason = reason;
            this.frequency = frequency;
        }

        public String getDay() {
            return day;
        }
        public int getIntDay(){
            if(this.day.equals("Monday")){
                return 1;
            }
            else if(this.day.equals("Tuesday")){
                return 2;
            }
            else if(this.day.equals("Wednesday")){
                return 3;
            }
            else if(this.day.equals("Thursday")){
                return 4;
            }
            else if(this.day.equals("Friday")){
                return 5;
            }
            else if(this.day.equals("Saturday")){
                return 6;
            }
            else if(this.day.equals("Sunday")){
                return 7;
            }
            else
                return 8;
        }

        public void setDay(String day) {
            if (!frequency.equalsIgnoreCase("everyday")) {
                this.day = day;
            }
        }

        public int getStartTime() {
            return startTime;
        }

        public void setStartTime(int startTime) {
            this.startTime = startTime;
        }

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getFrequency() {
            return frequency;
        }

        public void setFrequency(String frequency) {
            this.frequency = frequency;
            if (frequency.equalsIgnoreCase("everyday")) {
                this.day = "Everyday";
            }
        }

        @Override
        public String toString() {
            return "Day: " + day + ", Start Time: " + startTime + ", End Time: " + endTime + 
                   ", Reason: " + reason + ", Frequency: " + frequency;
        }
    }