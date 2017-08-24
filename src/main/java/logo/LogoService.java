package logo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jcraft.jsch.JSchException;
import database.DBUtils;
import dto.LogoModel;
import util.SystemProperties;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by ajopaul on 21/8/17.
 */
public class LogoService {

    String sqlQuery;
    public LogoService() throws Exception {
        
        sqlQuery = SystemProperties.getPropValue("logo_sql");
    }
    public String jsonGenerator() throws Exception {
        List<LogoModel> logos = getLogoRows();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.convertValue(logos,JsonNode.class);
        return jsonNode.toString();
    }

    public List<LogoModel> getLogoRows() throws Exception {
        DBUtils.ResultSetHandler resultSet = DBUtils.getInstance().fetchResults(sqlQuery);

        List<LogoModel> logos = new ArrayList<>();
        resultSet.rowsList.forEach( resultRow ->{
            LogoModel logoModel = new LogoModel();
            logoModel.brokerName = String.valueOf(resultRow.get(resultSet.columnNames.get("user")));
            logoModel.logoKey = String.valueOf(resultRow.get(resultSet.columnNames.get("logo")));
            logoModel.isLogoActive = String.valueOf(resultRow.get(resultSet.columnNames.get("logo_active"))).equals("Y");
            logoModel.profileImageKey = String.valueOf(resultRow.get(resultSet.columnNames.get("profile_image")));
            logoModel.isProfileActive = String.valueOf(resultRow.get(resultSet.columnNames.get("profile_image_active") )).equals("Y");
            logoModel.modifiedByUser = String.valueOf(resultRow.get(resultSet.columnNames.get("modified_user")));
            logoModel.modifiedByDate = (Date)resultRow.get(resultSet.columnNames.get("modified_date"));
            logos.add(logoModel);
        });

        return logos;
    }

    public Map<String, Integer> getLogoCounts() throws Exception {
        Map<String, Integer> countsMap = new HashMap<>();
        String logoSqlQuery = "select count(*) as count from white_label where logo is not null and logo_active = 'Y' and modified_by is not null and modified_by != 33393";
        int count = getCount(logoSqlQuery);
        countsMap.put("logo_active_count", count);

        String profileSqlQuery = "select count(*) as count from white_label where logo is not null and logo_active = 'Y' and modified_by is not null and modified_by != 33393";
        int profileCount = getCount(profileSqlQuery);
        countsMap.put("profile_active_count", profileCount);
        return countsMap;
    }


    private int getCount(String logoSqlQuery) throws Exception {
        DBUtils.ResultSetHandler resultSetHandler = DBUtils.getInstance().fetchResults(logoSqlQuery);
        int count = Integer.parseInt(String.valueOf(resultSetHandler.rowsList.get(0).get(0)));
        return count;
    }
}
