package weather;

/**
 * Created by Nimish on 04/11/2016.
 */
public class Weather {
    public double currentTemp;
    public double minTemp;
    public double maxTemp;
    public String desc;


        public double getTemp(){
            return currentTemp;
        }
        public void setTemp(double temp){
            this.currentTemp = temp;
        }
        public double getMinTemp() {
            return minTemp;
        }
        public void setMinTemp(double minTemp) {
            this.minTemp = minTemp;
        }
        public double getMaxTemp() {
            return maxTemp;
        }
        public void setMaxTemp(double maxTemp) {
            this.maxTemp = maxTemp;
        }
        public String getDesc(){
            return desc;
        }
        public void setDesc(String desc){
            this.desc = desc;
        }
    }




