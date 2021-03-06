package logo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.DBUtils;
import dto.LogoModel;
import util.SystemProperties;

import java.util.*;

/**
 * Created by ajopaul on 21/8/17.
 */
public class LogoService {

    String logoSql;
    String countSql;
    public LogoService() throws Exception {
        logoSql = SystemProperties.getPropValue("logo_sql");
        countSql = SystemProperties.getPropValue("count_sql");
    }
    public String jsonGenerator() throws Exception {
        List<LogoModel> logos = getLogoRows();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.convertValue(logos,JsonNode.class);
        return jsonNode.toString();
    }

    public List<LogoModel> getLogoRows() throws Exception {
        DBUtils.ResultSetHandler resultSet = DBUtils.fetchResults(logoSql);

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
        DBUtils.ResultSetHandler resultSetHandler = DBUtils.fetchResults(countSql);
        List<Object> row = resultSetHandler.rowsList.get(0);
        if(row.size() > 1) {
            countsMap.put("logo_active_count", Integer.parseInt(String.valueOf(row.get(0))));
            countsMap.put("profile_active_count", Integer.parseInt(String.valueOf(row.get(1))));
        }
        return countsMap;
    }


}
