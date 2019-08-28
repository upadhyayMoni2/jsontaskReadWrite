package jsonreadwriteprog;

import java.io.IOException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonReadWriteProg {

    public static void main(String[] args) throws IOException {

        double averageMonths, totalMonths = 0, totalEmployee = 0;
        String Current, departments, id;

        int year_hired;
        String file = FileReader.loadFileIntoString("json/Manager.json", "UTF-8");

        JSONObject mainobj = JSONObject.fromObject(file);

        JSONObject mainobjwrite = new JSONObject();
        JSONArray deparr = mainobj.getJSONArray("departments");
        JSONObject singleobjs = new JSONObject();
        JSONArray depArr = new JSONArray();
        JSONObject singleObjWrite = new JSONObject();

        for (int i = 0; i < deparr.size(); i++) {

            singleobjs = deparr.getJSONObject(i);
            totalEmployee += singleobjs.getInt("numberEmployees");
            totalMonths += singleobjs.getDouble("months");
            departments = singleobjs.getString("department_id") + " - " + singleobjs.getString("department_name");
            singleObjWrite.accumulate("departments", departments);

            if (singleobjs.getBoolean("current")) {

                Current = "IS CURRENT";

            } else {

                Current = "IS NOT CURRENT";
                
            }
            singleObjWrite.accumulate("current", Current);
            depArr.add(singleObjWrite);
            singleObjWrite.clear();
        }

        id = mainobj.getString("manager_number") + " - " + mainobj.getString("last_name");
        year_hired = Integer.parseInt(mainobj.getString("date_hire").substring(mainobj.getString("date_hire").length() - 4));
        averageMonths = totalMonths / deparr.size();

        mainobjwrite.accumulate("id", id);
        mainobjwrite.accumulate("yeat_hired", year_hired);
        mainobjwrite.accumulate("averageMonths", averageMonths);
        mainobjwrite.accumulate("totalEmployee", totalEmployee);
        mainobjwrite.accumulate("depArr", depArr);

        System.out.println(mainobjwrite);

        FileWriter.saveStringIntoFile("json/ManagerResult.json", mainobjwrite.toString());

    }

}
