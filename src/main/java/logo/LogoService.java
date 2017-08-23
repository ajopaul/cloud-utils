package logo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.DBUtils;
import dto.LogoModel;
import util.SystemProperties;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajopaul on 21/8/17.
 */
public class LogoService {
    DBUtils dbUtils;

    String sqlQuery;
    public LogoService() throws SQLException, ClassNotFoundException {
        dbUtils = DBUtils.getInstance();
        dbUtils.init();
        sqlQuery = SystemProperties.getPropValue("logo_sql");
    }
    public String jsonGenerator() throws SQLException, ClassNotFoundException {
        List<LogoModel> logos = getLogoRows();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.convertValue(logos,JsonNode.class);
        return jsonNode.toString();
    }

    public List<LogoModel> getLogoRows() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = dbUtils.fetchResults(sqlQuery);

        List<LogoModel> logos = new ArrayList<>();
        while(resultSet.next()){
            LogoModel logoModel = new LogoModel();
            logoModel.brokerName = resultSet.getString("user");
            logoModel.logoKey = resultSet.getString("logo");
            logoModel.isLogoActive = resultSet.getString("logo_active").equals("Y") ? true : false;

            logoModel.profileImageKey = resultSet.getString("profile_image");
            logoModel.isProfileActive = resultSet.getString("profile_image_active").equals("Y") ? true : false;
            logoModel.modifiedByUser = resultSet.getString("modified_user");
            logoModel.modifiedByDate = resultSet.getDate("modified_date");
            logos.add(logoModel);
        }

        return logos;
    }
}
