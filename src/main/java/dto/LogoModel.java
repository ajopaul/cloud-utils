package dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by ajopaul on 21/8/17.
 */
 @XmlRootElement
public class LogoModel {
    public String brokerName;
    public String logoKey;
    public String logoUrl;
    public boolean isLogoActive;

    public String profileImageKey;
    public String profileImageLogo;
    public boolean isProfileActive;

    public String modifiedByUser;
    public Date modifiedByDate;



}
