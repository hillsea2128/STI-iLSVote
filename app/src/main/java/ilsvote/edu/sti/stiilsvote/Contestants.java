package ilsvote.edu.sti.stiilsvote;

import java.io.File;

/**
 * Created by gilc2128 on 1/10/2018.
 */

public class Contestants {
    public String proj_title;
    public String class_sect;
    public String proj_desc;
    public String proj_ID;
    public String proj_group;
    public String short_proj_desc;
    public String raw_img;
    public Contestants(){
        super();
    }

    public Contestants(String title1, String sect1, String desc1, String bytes_img, String proj_ID, String proj_group) {
        super();
        this.proj_title = title1;
        this.class_sect = sect1;
        this.proj_desc = desc1;
        this.proj_ID = proj_ID;
        this.proj_group = proj_group;
        this.raw_img = bytes_img;

    }
    public void setProj_title(String proj_title) {
        this.proj_title = proj_title;
    }
    public void setProj_desc(String proj_desc) {
        this.proj_desc = proj_desc;
    }
    public void setClass_sect(String class_sect) {
        this.class_sect = class_sect;
    }
    public void setProj_ID(String proj_ID) {
        this.proj_ID = proj_ID;
    }
    public void setProj_group(String proj_group) {
        this.proj_group = proj_group;
    }
   // public void setImgbytes(String imgbytes) {
        //this.imgbytes = imgbytes;
   // }
    public String getProjGroupSec() {
        return class_sect + "  |  " + proj_group;
    }
    public String getlistviewdesc() {
        String hal = this.proj_desc.replaceAll("\n"," ");
        String finalstr;
        if(hal.length() > 50) {
            this.short_proj_desc = hal.substring(0,55) + "...";
            finalstr = this.short_proj_desc;
        } else {

            finalstr = this.proj_desc;
        }
        return finalstr;
    }
}
