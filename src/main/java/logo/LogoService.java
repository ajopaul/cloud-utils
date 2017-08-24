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
        DBUtils.ResultSetHandler resultSet = DBUtils.fetchResults(sqlQuery);

        List<LogoModel> logos = new ArrayList<>();
        resultSet.rowsList.forEach( resultRow ->{
            LogoModel logoModel = new LogoModel();
            Object user = getRowValue(resultSet.columnNames, resultRow, "user");
            logoModel.brokerName = null != user  ? String.valueOf(user) : null;

            Object logo = getRowValue(resultSet.columnNames, resultRow, "logo");
            logoModel.logoKey = null != logo  ? String.valueOf(logo) : null;

            logoModel.isLogoActive = String.valueOf(getRowValue(resultSet.columnNames, resultRow, "logo_active")).equals("Y");

            Object profileImageKey = getRowValue(resultSet.columnNames, resultRow, "profile_image");
            logoModel.profileImageKey = null != profileImageKey  ? String.valueOf(profileImageKey) : null;

            logoModel.isProfileActive = String.valueOf(getRowValue(resultSet.columnNames, resultRow, "profile_image_active")).equals("Y");

            Object modifiedByUser = getRowValue(resultSet.columnNames, resultRow, "modified_user");
            logoModel.modifiedByUser = null != modifiedByUser  ? String.valueOf(modifiedByUser) : null;

            logoModel.modifiedByDate = (Date)getRowValue(resultSet.columnNames, resultRow, "modified_date");

            logos.add(logoModel);
        });

        return logos;
    }

    public Object getRowValue(Map<String, Integer> colNamesMap, List<Object> resultRow, String colName) {
        return resultRow.get(colNamesMap.get(colName));
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
        DBUtils.ResultSetHandler resultSetHandler = DBUtils.fetchResults(logoSqlQuery);
        int count = Integer.parseInt(String.valueOf(resultSetHandler.rowsList.get(0).get(0)));
        return count;
    }
}
