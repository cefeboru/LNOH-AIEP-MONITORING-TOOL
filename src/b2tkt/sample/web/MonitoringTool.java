package b2tkt.sample.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import blackboard.db.*;
import blackboard.persist.Id;
import blackboard.persist.KeyNotFoundException;
import blackboard.persist.PersistenceException;
import blackboard.persist.course.CourseDbPersister;
import blackboard.platform.context.Context;
import blackboard.platform.context.ContextManagerFactory;
import blackboard.platform.email.BbMail;
import blackboard.platform.email.BbMailManagerFactory;
import blackboard.platform.plugin.PlugInUtil;
import blackboard.platform.validation.ConstraintViolationException;
import blackboard.data.ValidationException;
import blackboard.data.course.Course;
import blackboard.admin.data.course.CourseSite;
import blackboard.admin.persist.course.CloneConfig;
import blackboard.admin.persist.course.CourseSiteLoader;
import blackboard.admin.persist.course.CourseSitePersister;
import blackboard.persist.course.CourseDbLoader;

@Controller
public class MonitoringTool {

	// -------------------------------------------------------------------------------------------------------------------------------

	private int pageSize = 25;

	public String removeDiacritics(String str) {

		try {

			StringBuilder JSONString = new StringBuilder();

			JSONString.append("[");
			JSONString.append(
					"{\"base\":\"A\", \"letters\":\"[\\u0041\\u24B6\\uFF21\\u00C0\\u00C1\\u00C2\\u1EA6\\u1EA4\\u1EAA\\u1EA8\\u00C3\\u0100\\u0102\\u1EB0\\u1EAE\\u1EB4\\u1EB2\\u0226\\u01E0\\u00C4\\u01DE\\u1EA2\\u00C5\\u01FA\\u01CD\\u0200\\u0202\\u1EA0\\u1EAC\\u1EB6\\u1E00\\u0104\\u023A\\u2C6F]\"},");
			JSONString.append("{\"base\":\"AA\",\"letters\":\"[\\uA732]\"},");
			JSONString.append("{\"base\":\"AE\",\"letters\":\"[\\u00C6\\u01FC\\u01E2]\"},");
			JSONString.append("{\"base\":\"AO\",\"letters\":\"[\\uA734]\"},");
			JSONString.append("{\"base\":\"AU\",\"letters\":\"[\\uA736]\"},");
			JSONString.append("{\"base\":\"AV\",\"letters\":\"[\\uA738\\uA73A]\"},");
			JSONString.append("{\"base\":\"AY\",\"letters\":\"[\\uA73C]\"},");
			JSONString.append(
					"{\"base\":\"B\", \"letters\":\"[\\u0042\\u24B7\\uFF22\\u1E02\\u1E04\\u1E06\\u0243\\u0182\\u0181]\"},");
			JSONString.append(
					"{\"base\":\"C\", \"letters\":\"[\\u0043\\u24B8\\uFF23\\u0106\\u0108\\u010A\\u010C\\u00C7\\u1E08\\u0187\\u023B\\uA73E]\"},");
			JSONString.append(
					"{\"base\":\"D\", \"letters\":\"[\\u0044\\u24B9\\uFF24\\u1E0A\\u010E\\u1E0C\\u1E10\\u1E12\\u1E0E\\u0110\\u018B\\u018A\\u0189\\uA779]\"},");
			JSONString.append("{\"base\":\"DZ\",\"letters\":\"[\\u01F1\\u01C4]\"},");
			JSONString.append("{\"base\":\"Dz\",\"letters\":\"[\\u01F2\\u01C5]\"},");
			JSONString.append(
					"{\"base\":\"E\", \"letters\":\"[\\u0045\\u24BA\\uFF25\\u00C8\\u00C9\\u00CA\\u1EC0\\u1EBE\\u1EC4\\u1EC2\\u1EBC\\u0112\\u1E14\\u1E16\\u0114\\u0116\\u00CB\\u1EBA\\u011A\\u0204\\u0206\\u1EB8\\u1EC6\\u0228\\u1E1C\\u0118\\u1E18\\u1E1A\\u0190\\u018E]\"},");
			JSONString.append("{\"base\":\"F\", \"letters\":\"[\\u0046\\u24BB\\uFF26\\u1E1E\\u0191\\uA77B]\"},");
			JSONString.append(
					"{\"base\":\"G\", \"letters\":\"[\\u0047\\u24BC\\uFF27\\u01F4\\u011C\\u1E20\\u011E\\u0120\\u01E6\\u0122\\u01E4\\u0193\\uA7A0\\uA77D\\uA77E]\"},");
			JSONString.append(
					"{\"base\":\"H\", \"letters\":\"[\\u0048\\u24BD\\uFF28\\u0124\\u1E22\\u1E26\\u021E\\u1E24\\u1E28\\u1E2A\\u0126\\u2C67\\u2C75\\uA78D]\"},");
			JSONString.append(
					"{\"base\":\"I\", \"letters\":\"[\\u0049\\u24BE\\uFF29\\u00CC\\u00CD\\u00CE\\u0128\\u012A\\u012C\\u0130\\u00CF\\u1E2E\\u1EC8\\u01CF\\u0208\\u020A\\u1ECA\\u012E\\u1E2C\\u0197]\"},");
			JSONString.append("{\"base\":\"J\", \"letters\":\"[\\u004A\\u24BF\\uFF2A\\u0134\\u0248]\"},");
			JSONString.append(
					"{\"base\":\"K\", \"letters\":\"[\\u004B\\u24C0\\uFF2B\\u1E30\\u01E8\\u1E32\\u0136\\u1E34\\u0198\\u2C69\\uA740\\uA742\\uA744\\uA7A2]\"},");
			JSONString.append(
					"{\"base\":\"L\", \"letters\":\"[\\u004C\\u24C1\\uFF2C\\u013F\\u0139\\u013D\\u1E36\\u1E38\\u013B\\u1E3C\\u1E3A\\u0141\\u023D\\u2C62\\u2C60\\uA748\\uA746\\uA780]\"},");
			JSONString.append("{\"base\":\"LJ\",\"letters\":\"[\\u01C7]\"},");
			JSONString.append("{\"base\":\"Lj\",\"letters\":\"[\\u01C8]\"},");
			JSONString.append(
					"{\"base\":\"M\", \"letters\":\"[\\u004D\\u24C2\\uFF2D\\u1E3E\\u1E40\\u1E42\\u2C6E\\u019C]\"},");
			JSONString.append(
					"{\"base\":\"N\", \"letters\":\"[\\u004E\\u24C3\\uFF2E\\u01F8\\u0143\\u00D1\\u1E44\\u0147\\u1E46\\u0145\\u1E4A\\u1E48\\u0220\\u019D\\uA790\\uA7A4]\"},");
			JSONString.append("{\"base\":\"NJ\",\"letters\":\"[\\u01CA]\"},");
			JSONString.append("{\"base\":\"Nj\",\"letters\":\"[\\u01CB]\"},");
			JSONString.append(
					"{\"base\":\"O\", \"letters\":\"[\\u004F\\u24C4\\uFF2F\\u00D2\\u00D3\\u00D4\\u1ED2\\u1ED0\\u1ED6\\u1ED4\\u00D5\\u1E4C\\u022C\\u1E4E\\u014C\\u1E50\\u1E52\\u014E\\u022E\\u0230\\u00D6\\u022A\\u1ECE\\u0150\\u01D1\\u020C\\u020E\\u01A0\\u1EDC\\u1EDA\\u1EE0\\u1EDE\\u1EE2\\u1ECC\\u1ED8\\u01EA\\u01EC\\u00D8\\u01FE\\u0186\\u019F\\uA74A\\uA74C]\"},");
			JSONString.append("{\"base\":\"OI\",\"letters\":\"[\\u01A2]\"},");
			JSONString.append("{\"base\":\"OO\",\"letters\":\"[\\uA74E]\"},");
			JSONString.append("{\"base\":\"OU\",\"letters\":\"[\\u0222]\"},");
			JSONString.append(
					"{\"base\":\"P\", \"letters\":\"[\\u0050\\u24C5\\uFF30\\u1E54\\u1E56\\u01A4\\u2C63\\uA750\\uA752\\uA754]\"},");
			JSONString.append("{\"base\":\"Q\", \"letters\":\"[\\u0051\\u24C6\\uFF31\\uA756\\uA758\\u024A]\"},");
			JSONString.append(
					"{\"base\":\"R\", \"letters\":\"[\\u0052\\u24C7\\uFF32\\u0154\\u1E58\\u0158\\u0210\\u0212\\u1E5A\\u1E5C\\u0156\\u1E5E\\u024C\\u2C64\\uA75A\\uA7A6\\uA782]\"},");
			JSONString.append(
					"{\"base\":\"S\", \"letters\":\"[\\u0053\\u24C8\\uFF33\\u1E9E\\u015A\\u1E64\\u015C\\u1E60\\u0160\\u1E66\\u1E62\\u1E68\\u0218\\u015E\\u2C7E\\uA7A8\\uA784]\"},");
			JSONString.append(
					"{\"base\":\"T\", \"letters\":\"[\\u0054\\u24C9\\uFF34\\u1E6A\\u0164\\u1E6C\\u021A\\u0162\\u1E70\\u1E6E\\u0166\\u01AC\\u01AE\\u023E\\uA786]\"},");
			JSONString.append("{\"base\":\"TZ\",\"letters\":\"[\\uA728]\"},");
			JSONString.append(
					"{\"base\":\"U\", \"letters\":\"[\\u0055\\u24CA\\uFF35\\u00D9\\u00DA\\u00DB\\u0168\\u1E78\\u016A\\u1E7A\\u016C\\u00DC\\u01DB\\u01D7\\u01D5\\u01D9\\u1EE6\\u016E\\u0170\\u01D3\\u0214\\u0216\\u01AF\\u1EEA\\u1EE8\\u1EEE\\u1EEC\\u1EF0\\u1EE4\\u1E72\\u0172\\u1E76\\u1E74\\u0244]\"},");
			JSONString.append(
					"{\"base\":\"V\", \"letters\":\"[\\u0056\\u24CB\\uFF36\\u1E7C\\u1E7E\\u01B2\\uA75E\\u0245]\"},");
			JSONString.append("{\"base\":\"VY\",\"letters\":\"[\\uA760]\"},");
			JSONString.append(
					"{\"base\":\"W\", \"letters\":\"[\\u0057\\u24CC\\uFF37\\u1E80\\u1E82\\u0174\\u1E86\\u1E84\\u1E88\\u2C72]\"},");
			JSONString.append("{\"base\":\"X\", \"letters\":\"[\\u0058\\u24CD\\uFF38\\u1E8A\\u1E8C]\"},");
			JSONString.append(
					"{\"base\":\"Y\", \"letters\":\"[\\u0059\\u24CE\\uFF39\\u1EF2\\u00DD\\u0176\\u1EF8\\u0232\\u1E8E\\u0178\\u1EF6\\u1EF4\\u01B3\\u024E\\u1EFE]\"},");
			JSONString.append(
					"{\"base\":\"Z\", \"letters\":\"[\\u005A\\u24CF\\uFF3A\\u0179\\u1E90\\u017B\\u017D\\u1E92\\u1E94\\u01B5\\u0224\\u2C7F\\u2C6B\\uA762]\"},");
			JSONString.append(
					"{\"base\":\"a\", \"letters\":\"[\\u0061\\u24D0\\uFF41\\u1E9A\\u00E0\\u00E1\\u00E2\\u1EA7\\u1EA5\\u1EAB\\u1EA9\\u00E3\\u0101\\u0103\\u1EB1\\u1EAF\\u1EB5\\u1EB3\\u0227\\u01E1\\u00E4\\u01DF\\u1EA3\\u00E5\\u01FB\\u01CE\\u0201\\u0203\\u1EA1\\u1EAD\\u1EB7\\u1E01\\u0105\\u2C65\\u0250]\"},");
			JSONString.append("{\"base\":\"aa\",\"letters\":\"[\\uA733]\"},");
			JSONString.append("{\"base\":\"ae\",\"letters\":\"[\\u00E6\\u01FD\\u01E3]\"},");
			JSONString.append("{\"base\":\"ao\",\"letters\":\"[\\uA735]\"},");
			JSONString.append("{\"base\":\"au\",\"letters\":\"[\\uA737]\"},");
			JSONString.append("{\"base\":\"av\",\"letters\":\"[\\uA739\\uA73B]\"},");
			JSONString.append("{\"base\":\"ay\",\"letters\":\"[\\uA73D]\"},");
			JSONString.append(
					"{\"base\":\"b\", \"letters\":\"[\\u0062\\u24D1\\uFF42\\u1E03\\u1E05\\u1E07\\u0180\\u0183\\u0253]\"},");
			JSONString.append(
					"{\"base\":\"c\", \"letters\":\"[\\u0063\\u24D2\\uFF43\\u0107\\u0109\\u010B\\u010D\\u00E7\\u1E09\\u0188\\u023C\\uA73F\\u2184]\"},");
			JSONString.append(
					"{\"base\":\"d\", \"letters\":\"[\\u0064\\u24D3\\uFF44\\u1E0B\\u010F\\u1E0D\\u1E11\\u1E13\\u1E0F\\u0111\\u018C\\u0256\\u0257\\uA77A]\"},");
			JSONString.append("{\"base\":\"dz\",\"letters\":\"[\\u01F3\\u01C6]\"},");
			JSONString.append(
					"{\"base\":\"e\", \"letters\":\"[\\u0065\\u24D4\\uFF45\\u00E8\\u00E9\\u00EA\\u1EC1\\u1EBF\\u1EC5\\u1EC3\\u1EBD\\u0113\\u1E15\\u1E17\\u0115\\u0117\\u00EB\\u1EBB\\u011B\\u0205\\u0207\\u1EB9\\u1EC7\\u0229\\u1E1D\\u0119\\u1E19\\u1E1B\\u0247\\u025B\\u01DD]\"},");
			JSONString.append("{\"base\":\"f\", \"letters\":\"[\\u0066\\u24D5\\uFF46\\u1E1F\\u0192\\uA77C]\"},");
			JSONString.append(
					"{\"base\":\"g\", \"letters\":\"[\\u0067\\u24D6\\uFF47\\u01F5\\u011D\\u1E21\\u011F\\u0121\\u01E7\\u0123\\u01E5\\u0260\\uA7A1\\u1D79\\uA77F]\"},");
			JSONString.append(
					"{\"base\":\"h\", \"letters\":\"[\\u0068\\u24D7\\uFF48\\u0125\\u1E23\\u1E27\\u021F\\u1E25\\u1E29\\u1E2B\\u1E96\\u0127\\u2C68\\u2C76\\u0265]\"},");
			JSONString.append("{\"base\":\"hv\",\"letters\":\"[\\u0195]\"},");
			JSONString.append(
					"{\"base\":\"i\", \"letters\":\"[\\u0069\\u24D8\\uFF49\\u00EC\\u00ED\\u00EE\\u0129\\u012B\\u012D\\u00EF\\u1E2F\\u1EC9\\u01D0\\u0209\\u020B\\u1ECB\\u012F\\u1E2D\\u0268\\u0131]\"},");
			JSONString.append("{\"base\":\"j\", \"letters\":\"[\\u006A\\u24D9\\uFF4A\\u0135\\u01F0\\u0249]\"},");
			JSONString.append(
					"{\"base\":\"k\", \"letters\":\"[\\u006B\\u24DA\\uFF4B\\u1E31\\u01E9\\u1E33\\u0137\\u1E35\\u0199\\u2C6A\\uA741\\uA743\\uA745\\uA7A3]\"},");
			JSONString.append(
					"{\"base\":\"l\", \"letters\":\"[\\u006C\\u24DB\\uFF4C\\u0140\\u013A\\u013E\\u1E37\\u1E39\\u013C\\u1E3D\\u1E3B\\u017F\\u0142\\u019A\\u026B\\u2C61\\uA749\\uA781\\uA747]\"},");
			JSONString.append("{\"base\":\"lj\",\"letters\":\"[\\u01C9]\"},");
			JSONString.append(
					"{\"base\":\"m\", \"letters\":\"[\\u006D\\u24DC\\uFF4D\\u1E3F\\u1E41\\u1E43\\u0271\\u026F]\"},");
			JSONString.append(
					"{\"base\":\"n\", \"letters\":\"[\\u006E\\u24DD\\uFF4E\\u01F9\\u0144\\u00F1\\u1E45\\u0148\\u1E47\\u0146\\u1E4B\\u1E49\\u019E\\u0272\\u0149\\uA791\\uA7A5]\"},");
			JSONString.append("{\"base\":\"nj\",\"letters\":\"[\\u01CC]\"},");
			JSONString.append(
					"{\"base\":\"o\", \"letters\":\"[\\u006F\\u24DE\\uFF4F\\u00F2\\u00F3\\u00F4\\u1ED3\\u1ED1\\u1ED7\\u1ED5\\u00F5\\u1E4D\\u022D\\u1E4F\\u014D\\u1E51\\u1E53\\u014F\\u022F\\u0231\\u00F6\\u022B\\u1ECF\\u0151\\u01D2\\u020D\\u020F\\u01A1\\u1EDD\\u1EDB\\u1EE1\\u1EDF\\u1EE3\\u1ECD\\u1ED9\\u01EB\\u01ED\\u00F8\\u01FF\\u0254\\uA74B\\uA74D\\u0275]\"},");
			JSONString.append("{\"base\":\"oi\",\"letters\":\"[\\u01A3]\"},");
			JSONString.append("{\"base\":\"ou\",\"letters\":\"[\\u0223]\"},");
			JSONString.append("{\"base\":\"oo\",\"letters\":\"[\\uA74F]\"},");
			JSONString.append(
					"{\"base\":\"p\",\"letters\":\"[\\u0070\\u24DF\\uFF50\\u1E55\\u1E57\\u01A5\\u1D7D\\uA751\\uA753\\uA755]\"},");
			JSONString.append("{\"base\":\"q\",\"letters\":\"[\\u0071\\u24E0\\uFF51\\u024B\\uA757\\uA759]\"},");
			JSONString.append(
					"{\"base\":\"r\",\"letters\":\"[\\u0072\\u24E1\\uFF52\\u0155\\u1E59\\u0159\\u0211\\u0213\\u1E5B\\u1E5D\\u0157\\u1E5F\\u024D\\u027D\\uA75B\\uA7A7\\uA783]\"},");
			JSONString.append(
					"{\"base\":\"s\",\"letters\":\"[\\u0073\\u24E2\\uFF53\\u00DF\\u015B\\u1E65\\u015D\\u1E61\\u0161\\u1E67\\u1E63\\u1E69\\u0219\\u015F\\u023F\\uA7A9\\uA785\\u1E9B]\"},");
			JSONString.append(
					"{\"base\":\"t\",\"letters\":\"[\\u0074\\u24E3\\uFF54\\u1E6B\\u1E97\\u0165\\u1E6D\\u021B\\u0163\\u1E71\\u1E6F\\u0167\\u01AD\\u0288\\u2C66\\uA787]\"},");
			JSONString.append("{\"base\":\"tz\",\"letters\":\"[\\uA729]\"},");
			JSONString.append(
					"{\"base\":\"u\",\"letters\":\"[\\u0075\\u24E4\\uFF55\\u00F9\\u00FA\\u00FB\\u0169\\u1E79\\u016B\\u1E7B\\u016D\\u00FC\\u01DC\\u01D8\\u01D6\\u01DA\\u1EE7\\u016F\\u0171\\u01D4\\u0215\\u0217\\u01B0\\u1EEB\\u1EE9\\u1EEF\\u1EED\\u1EF1\\u1EE5\\u1E73\\u0173\\u1E77\\u1E75\\u0289]\"},");
			JSONString.append(
					"{\"base\":\"v\",\"letters\":\"[\\u0076\\u24E5\\uFF56\\u1E7D\\u1E7F\\u028B\\uA75F\\u028C]\"},");
			JSONString.append("{\"base\":\"vy\",\"letters\":\"[\\uA761]\"},");
			JSONString.append(
					"{\"base\":\"w\",\"letters\":\"[\\u0077\\u24E6\\uFF57\\u1E81\\u1E83\\u0175\\u1E87\\u1E85\\u1E98\\u1E89\\u2C73]\"},");
			JSONString.append("{\"base\":\"x\",\"letters\":\"[\\u0078\\u24E7\\uFF58\\u1E8B\\u1E8D]\"},");
			JSONString.append(
					"{\"base\":\"y\",\"letters\":\"[\\u0079\\u24E8\\uFF59\\u1EF3\\u00FD\\u0177\\u1EF9\\u0233\\u1E8F\\u00FF\\u1EF7\\u1E99\\u1EF5\\u01B4\\u024F\\u1EFF]\"},");
			JSONString.append(
					"{\"base\":\"z\",\"letters\":\"[\\u007A\\u24E9\\uFF5A\\u017A\\u1E91\\u017C\\u017E\\u1E93\\u1E95\\u01B6\\u0225\\u0240\\u2C6C\\uA763]\"}");
			JSONString.append("]");

			JSONArray defaultDiacriticsRemovalMap = new JSONArray(JSONString.toString());

			for (int i = 0; i < defaultDiacriticsRemovalMap.length(); i++) {
				str = str.replaceAll(((JSONObject) defaultDiacriticsRemovalMap.get(i)).getString("letters"),
						((JSONObject) defaultDiacriticsRemovalMap.get(i)).getString("base"));
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return str;
	}

	public String getStatus(String PrevStatus, int diasVerde, int diasAmarillo, int diasNaranja) {

		String Status = "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/";
		String Msg = "";
		int dateDiff = -1;

		if (PrevStatus == null) {

			Status += "rojo";
			Msg = "Nunca Conectado";
		} else {

			PrevStatus = PrevStatus.split(" ")[0];
			dateDiff = Integer.parseInt(PrevStatus);
		}

		if (dateDiff >= 0 && dateDiff < diasVerde) {

			Status += "verde";
			Msg = "Conectado durante los últimos "+ diasVerde +" días";
		} else if (dateDiff >= diasVerde && dateDiff < diasAmarillo) {

			Status += "amarillo";
			Msg = diasAmarillo +" dias sin conexión";
		} else if (dateDiff >= diasNaranja) {

			Status += "naranja";
			Msg = "Más de "+ diasNaranja +" d&iacute;as sin conexi&oacute;n";
		}

		Status += ".png' style='height: 25px;' alt='" + Msg + "' title='" + Msg + "'>";

		return Status;
	}

	// CONTROL DE ESTUDIANTES SP
	public String getStudentControl(String Headquarter, int pageNumber, String modalidad) {

		ConnectionManager cManager = null;
		Connection conn = null;
		PreparedStatement selectQuery = null;

		String RESULTS = "";

		try {

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			StringBuilder TableData = new StringBuilder();

			int firstRow = (pageNumber - 1) * pageSize + 1;
			int lastRow = (pageNumber * pageSize);

			TableData.append("SELECT * FROM ( SELECT rownum rnum, a.* from (");
			TableData.append("SELECT US.PK1, US.LASTNAME, US.FIRSTNAME, EXTRACT(year from US.DTCREATED) \"COHORTE\","
					+ " US.EMAIL, US.LAST_LOGIN_DATE, (systimestamp - US.LAST_LOGIN_DATE) \"Date Diff\" ,IR.ROLE_ID, US.B_PHONE_1"
					+ " FROM USERS US INNER JOIN INSTITUTION_ROLES IR ON US.INSTITUTION_ROLES_PK1=IR.PK1 ");

			if (Headquarter.equals("Todo") && modalidad.equals("Todo")) {
				TableData.append("WHERE IR.ROLE_ID IN ('Online','Semipresencial','Presencial') ");
			} else if (Headquarter.equals("Todo")) {
				TableData.append("WHERE US.INSTITUTION_ROLES_PK1=" + modalidad + " ");
			} else if (modalidad.equals("Todo")) {
				TableData.append("WHERE IR.ROLE_ID IN ('Online','Semipresencial','Presencial') ");
				TableData.append("AND US.B_PHONE_1 ='" + Headquarter + "' ");
			} else {
				TableData.append("WHERE US.INSTITUTION_ROLES_PK1=" + modalidad + " ");
				TableData.append("AND US.B_PHONE_1= '" + Headquarter + "' ");
			}
			TableData.append("AND US.SYSTEM_ROLE = 'N' ");
			TableData.append("AND US.DATA_SRC_PK1 != 2 ");
			TableData.append(" ORDER BY LAST_LOGIN_DATE ASC ");
			TableData.append(") a ) WHERE rnum BETWEEN "+firstRow+" AND "+lastRow);
			
			
			/*TableData.append("SELECT * FROM ( SELECT rownum rnum, a.* from (");
			TableData.append("SELECT US.PK1, US.LASTNAME, US.FIRSTNAME, EXTRACT(year from US.DTCREATED) \"COHORTE\","
					+ " US.EMAIL, US.LAST_LOGIN_DATE, (systimestamp - US.LAST_LOGIN_DATE) \"Date Diff\" ,IR.ROLE_ID, US.B_PHONE_1"
					+ " FROM USERS US INNER JOIN INSTITUTION_ROLES IR ON US.INSTITUTION_ROLES_PK1=IR.PK1 ");

			if (Headquarter.equals("Todo") && modalidad.equals("Todo")) {
				TableData.append("WHERE IR.ROLE_ID IN ('Online','Semipresencial','Presencial') ");
			} else if (Headquarter.equals("Todo")) {
				TableData.append("WHERE US.INSTITUTION_ROLES_PK1= ? ");
			} else if (modalidad.equals("Todo")) {
				TableData.append("WHERE IR.ROLE_ID IN ('Online','Semipresencial','Presencial') ");
				TableData.append("AND US.B_PHONE_1 = ? ");
			} else {
				TableData.append("WHERE US.INSTITUTION_ROLES_PK1= ? ");
				TableData.append("AND US.B_PHONE_1= ? ");
			}
			TableData.append("AND US.SYSTEM_ROLE = 'N' ");
			TableData.append("AND US.DATA_SRC_PK1 != 2 ");
			TableData.append(" ORDER BY LAST_LOGIN_DATE ASC ");
			TableData.append(") a ) WHERE rnum BETWEEN "+firstRow+" AND "+lastRow);*/


			String query = TableData.toString();
			System.out.println("Monitoring Tool - Data Query : " + query);

			selectQuery = conn.prepareStatement(query, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			ResultSet rSet = selectQuery.executeQuery();

			RESULTS = "<table id='grid-basic' class='table table-condensed table-hover table-striped'>";
			RESULTS += "<thead>";
			RESULTS += "<tr>";

			String ColumnName = "";

			RESULTS += "<th data-column-id='modalidad'>MODALIDAD</th>";
			RESULTS += "<th data-column-id='sede'>SEDE</th>";
			
			ColumnName = "APELLIDO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "NOMBRES";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "EMAIL";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "ULTIMO INGRESO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "ESTADO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "GESTIÓN";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "CONTACTO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "HISTORIAL";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			RESULTS += "</tr>";
			RESULTS += "</thead>";
			RESULTS += "<tbody>";

			ArrayList<String> StudentIds = new ArrayList<String>();
			ArrayList<ArrayList<String>> TableInfo = new ArrayList<ArrayList<String>>();
			ArrayList<String> TempInfo = null;

			String Modalidad = "";
			String Sede = "";
			String LastName = "";
			String FirstName = "";
			String Email = "";
			String LastAccess = "";
			String Status = "";
			String SentEmail = "";
			String EmailResponse = "";
			String Contact = "";
			String History = "";

			PreparedStatement selectQuerySM = conn.prepareStatement(
					"SELECT PK1, CONTACT_TYPE FROM LNOH_STUDENT_MANAGEMENT GROUP BY PK1, CONTACT_TYPE",
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			ResultSet rSetSM = selectQuerySM.executeQuery();

			ArrayList<String> SMStudentIds = new ArrayList<String>();
			ArrayList<String> SMType = new ArrayList<String>();
			ArrayList<String> SNoContact = new ArrayList<String>();

			while (rSetSM.next()) {

				SMStudentIds.add(rSetSM.getString(1));
				SMType.add(rSetSM.getString(2));
			}

			selectQuerySM = conn.prepareStatement(
					"SELECT PK1 FROM LNOH_STUDENT_MANAGEMENT WHERE STUDENT_RESPONSE = 'NO CONTACTAR'",
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			rSetSM = selectQuerySM.executeQuery();

			while (rSetSM.next()) {

				SNoContact.add(rSetSM.getString(1));
			}

			while (rSet.next()) {
				
				
				StudentIds.add(rSet.getString("PK1"));
				Modalidad = rSet.getString("ROLE_ID");
				Sede = rSet.getString("B_PHONE_1");
				LastName = rSet.getString("LASTNAME");
				FirstName = rSet.getString("FIRSTNAME");
				//Cohorte = rSet.getString("COHORTE");
				Email = rSet.getString("EMAIL");
				LastAccess = rSet.getString("LAST_LOGIN_DATE");
				Status = rSet.getString("Date Diff");

				if (LastAccess == null) {

					LastAccess = "NUNCA";
				}
				if (Email == null) {

					Email = "No Tiene Email";
					Contact = "<img id='Management" + rSet.getString("PK1")
							+ "' src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/registra.png' style='height: 85%;'>";
				} else {
					Contact = "<input id='Chk" + rSet.getString("PK1") + "' type='checkbox' name='chk' value='" + Email
							+ "'><img id='Management" + rSet.getString("PK1")
							+ "' src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/registra.png' style='height: 85%;' onclick=\"Management(this)\">";
				}

				if (SNoContact.indexOf(rSet.getString("PK1")) == -1) {
					
					ResultSet tempResult = conn.createStatement().executeQuery("SELECT * FROM LNOH_STUDREPORT_SETTINGS");
					if(tempResult.next()){
						int diasVerde = tempResult.getInt("DIAS_ESTADO_VERDE");
						int diasAmarillo  = tempResult.getInt("DIAS_ESTADO_AMARILLO");
						int diasNaranja = tempResult.getInt("DIAS_ESTADO_NARANJA");
						Status = getStatus(Status, diasVerde, diasAmarillo, diasNaranja);
					} else {
						Status = getStatus(Status, 7, 14, 14);
					}
					
				} else {

					Status = "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/gris.png' style='height: 25px;' alt='NO Volver a Contactar' title='NO Volver a Contactar'>";
					Contact = "<img id='Management" + rSet.getString("PK1")
							+ "' src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/registra.png' style='height: 85%;'>";
				}

				if (Status.indexOf("verde") != -1) {

					SentEmail = "<img id='Sent" + rSet.getString("PK1")
							+ "' src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/no.png' style='height: 85%;'>";
					EmailResponse = "<img id='Response" + rSet.getString("PK1")
							+ "' src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/no.png' style='height: 85%;'>";
				} else {

					SentEmail = "<img id='Sent" + rSet.getString("PK1")
							+ "' src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' style='height: 85%;'>";
					EmailResponse = "<img id='Response" + rSet.getString("PK1")
							+ "' src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' style='height: 85%;'>";

					int pos = SMStudentIds.indexOf(rSet.getString("PK1"));

					if (pos != -1) {

						for (int i = pos; i < SMStudentIds.size(); i++) {

							if (SMType.get(i).equals("C") && SMStudentIds.get(i).equals(rSet.getString("PK1"))) {

								SentEmail = "<img id='Sent" + rSet.getString("PK1")
										+ "' src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/chk.png' style='height: 85%;'>";
							}
							if (SMType.get(i).equals("E") && SMStudentIds.get(i).equals(rSet.getString("PK1"))) {

								EmailResponse = "<img id='Response" + rSet.getString("PK1")
										+ "' src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/chk.png' style='height: 85%;'>";
							}
						}
					}
				}

				History = "<img id='History" + rSet.getString("PK1")
						+ "' src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/historial.png' style='height: 85%;'"
						+ "onclick='studentRecord(this);'>";

				TempInfo = new ArrayList<String>();
				TempInfo.add(Modalidad);
				TempInfo.add(Sede);
				TempInfo.add(LastName);
				TempInfo.add(FirstName);
				//TempInfo.add(Cohorte);
				TempInfo.add(Email);
				TempInfo.add(LastAccess.split(" ")[0]);
				TempInfo.add(Status);
				TempInfo.add(SentEmail);
				TempInfo.add(EmailResponse);
				TempInfo.add(Contact);
				TempInfo.add(History);

				TableInfo.add(TempInfo);
			}

			rSet.close();

			Long count = 0L;

			if (TableInfo.size() <= 0) {
				RESULTS += "<tr> No se encontraron elementos! </tr>";
			} else
				for (int i = 0; i < TableInfo.size(); i++) {
					int j=0;
					Modalidad = TableInfo.get(i).get(j++);
					Sede = TableInfo.get(i).get(j++);
					LastName = TableInfo.get(i).get(j++);
					FirstName = TableInfo.get(i).get(j++);
					//Cohorte = TableInfo.get(i).get(j++);
					Email = TableInfo.get(i).get(j++);
					LastAccess = TableInfo.get(i).get(j++);
					Status = TableInfo.get(i).get(j++);
					SentEmail = TableInfo.get(i).get(j++);
					EmailResponse = TableInfo.get(i).get(j++);
					Contact = TableInfo.get(i).get(j++);
					History = TableInfo.get(i).get(j++);

					RESULTS += "<tr>";
					RESULTS += "<td id='Modalidad" + StudentIds.get(i) + "'>" + Modalidad + "</td>";
					RESULTS += "<td id='Sede" + StudentIds.get(i) + "'>" + Sede + "</td>";
					RESULTS += "<td id='Last" + StudentIds.get(i) + "'>" + LastName + "</td>";
					RESULTS += "<td id='First" + StudentIds.get(i) + "'>" + FirstName + "</td>";
					//RESULTS += "<td id='Cohorte" + StudentIds.get(i) + "'>" + Cohorte + "</td>";
					RESULTS += "<td id='Email" + StudentIds.get(i) + "'>" + Email + "</td>";
					RESULTS += "<td id='LastAccess" + StudentIds.get(i) + "'>" + LastAccess + "</td>";
					RESULTS += "<td id='Status" + StudentIds.get(i) + "'>" + Status + "</td>";
					RESULTS += "<td id='SentResponse" + StudentIds.get(i) + "'>" + SentEmail + EmailResponse + "</td>";
					RESULTS += "<td id='Contact" + StudentIds.get(i) + "'>" + Contact + "</td>";
					RESULTS += "<td id='HistoryR" + StudentIds.get(i) + "'>" + History + "</td>";

					RESULTS += "</tr>";

					count++;
				}

			RESULTS += "</tbody>";
			RESULTS += "</table>";
			//RESULTS += "<label>Total: " + count + "</label>";

			// rSet.close();

			selectQuery.close();

		} catch (java.sql.SQLException sE) {

			RESULTS += "java.sql.SQLException sE: " + sE.toString();

		} catch (ConnectionNotAvailableException cE) {

			RESULTS += "ConnectionNotAvailableException cE :" + cE.toString();
		} finally {

			if (conn != null) {
				cManager.releaseConnection(conn);
			}
		}

		return RESULTS;

	}
	
	public String getModulos() throws SQLException{
		
		ConnectionManager cManager = null;
		Connection conn = null;
		StringBuilder RESULTS = new StringBuilder("<option value='Todo'>Todo</option>");
		cManager = BbDatabase.getDefaultInstance().getConnectionManager();
		try {
			conn = cManager.getConnection();
			String query = "SELECT DISTINCT(MODULO) FROM LNOH_HORARIO_DOCENTE";
			ResultSet rs = conn.createStatement().executeQuery(query);
			while(rs.next()){
				String modulo = rs.getString("MODULO");
				RESULTS.append("<option value='"+modulo+"'>"+modulo+"</option>");
			}
		} catch (ConnectionNotAvailableException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(conn != null){
				cManager.releaseConnection(conn);
				conn.close();
				conn = null;
				cManager = null;
			}
		}
		
		return RESULTS.toString();
	}
	
	private String getAnios() throws SQLException {
		ConnectionManager cManager = null;
		Connection conn = null;
		StringBuilder RESULTS = new StringBuilder("<option value='Todo'>Todo</option>");
		cManager = BbDatabase.getDefaultInstance().getConnectionManager();
		try {
			conn = cManager.getConnection();
			String query = "SELECT DISTINCT(ANO_PROCESO) FROM LNOH_HORARIO_DOCENTE";
			ResultSet rs = conn.createStatement().executeQuery(query);
			while(rs.next()){
				String year = rs.getString("ANO_PROCESO");
				RESULTS.append("<option value='"+year+"'>"+year+"</option>");
			}
		} catch (ConnectionNotAvailableException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(conn != null){
				cManager.releaseConnection(conn);
				conn.close();
				conn = null;
				cManager = null;
			}
		}
		
		return RESULTS.toString();
	}
	
	private String getSemestres() throws SQLException {
		ConnectionManager cManager = null;
		Connection conn = null;
		StringBuilder RESULTS = new StringBuilder("<option value='Todo'>Todo</option>");
		cManager = BbDatabase.getDefaultInstance().getConnectionManager();
		try {
			conn = cManager.getConnection();
			String query = "SELECT DISTINCT(SEMESTRE_PROCESO) FROM LNOH_HORARIO_DOCENTE";
			ResultSet rs = conn.createStatement().executeQuery(query);
			while(rs.next()){
				String year = rs.getString("SEMESTRE_PROCESO");
				RESULTS.append("<option value='"+year+"'>"+year+"</option>");
			}
		} catch (ConnectionNotAvailableException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(conn != null){
				cManager.releaseConnection(conn);
				conn.close();
				conn = null;
				cManager = null;
			}
		}
		
		return RESULTS.toString();
	}
	
	// Obtener todas las modalidades
	public String getModalidades() {

		ConnectionManager cManager = null;
		Connection conn = null;
		PreparedStatement selectQuery = null;

		String RESULTS = "";

		try {

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			StringBuilder TableData = new StringBuilder();
			TableData.append("SELECT PK1,ROLE_NAME FROM INSTITUTION_ROLES ");
			TableData.append("WHERE ROLE_ID IN('Semipresencial','Presencial','Online') ");
			TableData.append("ORDER BY PK1 ASC");

			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rSet = selectQuery.executeQuery();

			RESULTS = "<select name='Modalidades' id='Modalidades'>";
			RESULTS += "<option value='Todo' selected>Todo</option>";

			while (rSet.next()) {
				String primaryKey = rSet.getString("PK1");
				String Name = rSet.getString("ROLE_NAME");

				RESULTS += "<option style=\"margin:0 10px 0 10px;\" value='" + primaryKey + "'>" + Name + "</option>";
			}

			RESULTS += "</select>";

			rSet.close();

			selectQuery.close();

		} catch (java.sql.SQLException sE) {

			RESULTS += "java.sql.SQLException sE: " + sE.toString();

		} catch (ConnectionNotAvailableException cE) {

			RESULTS += "ConnectionNotAvailableException cE :" + cE.toString();
		} finally {

			if (conn != null) {
				cManager.releaseConnection(conn);
			}
		}

		return RESULTS;
	}
	
	// Obtener todas las modalidades de los cursos
	public String getCourseModalities() {

		ConnectionManager cManager = null;
		Connection conn = null;
		PreparedStatement selectQuery = null;

		String RESULTS = "";

		try {

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			StringBuilder TableData = new StringBuilder();
			TableData.append("SELECT DISTINCT(substr(course_id,instr(course_id,'-',-1) + 1,length(course_id))) FROM COURSE_MAIN  where row_status = 0 and course_id like '%-%-%-%-%'");

			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rSet = selectQuery.executeQuery();

			RESULTS = "<select name='Modalidades' id='Modalidades'>";
			RESULTS += "<option value='Todo' selected>Todo</option>";

			String Name = "";
			
			while (rSet.next()) {
				
				Name = rSet.getString(1);

				RESULTS += "<option style=\"margin:0 10px 0 10px;\" value='" + Name + "'>" + Name + "</option>";
			}

			RESULTS += "</select>";

			rSet.close();

			selectQuery.close();

		} catch (java.sql.SQLException sE) {

			RESULTS += "java.sql.SQLException sE: " + sE.toString();

		} catch (ConnectionNotAvailableException cE) {

			RESULTS += "ConnectionNotAvailableException cE :" + cE.toString();
		} finally {

			if (conn != null) {
				cManager.releaseConnection(conn);
			}
		}
		return RESULTS;
	}

	// Obtener todas las sedes - estudiantes
	public String getHeadquarters() {

		ConnectionManager cManager = null;
		Connection conn = null;
		PreparedStatement selectQuery = null;

		String RESULTS = "";

		try {

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			StringBuilder TableData = new StringBuilder();
			TableData.append(" SELECT DISTINCT(B_PHONE_1) FROM USERS US ");
			TableData.append(" WHERE US.INSTITUTION_ROLES_PK1 IN( ");
			TableData.append(
					" SELECT IR.PK1 FROM INSTITUTION_ROLES IR WHERE IR.ROLE_ID IN('Online','Semipresencial','Presencial')) ");
			TableData.append(" AND B_PHONE_1 IS NOT NULL ");

			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rSet = selectQuery.executeQuery();

			RESULTS = "<select name='Headquarters' id='Headquarters'>";
			RESULTS += "<option value='Todo' selected>Todo</option>";

			while (rSet.next()) {
				String value = rSet.getString(1);
				String Name = rSet.getString(1);

				RESULTS += "<option style=\"margin:0 10px 0 10px;\" value='" + value + "'>" + Name + "</option>";
			}

			RESULTS += "</select>";

			rSet.close();

			selectQuery.close();

		} catch (java.sql.SQLException sE) {

			RESULTS += "java.sql.SQLException sE: " + sE.toString();

		} catch (ConnectionNotAvailableException cE) {

			RESULTS += "ConnectionNotAvailableException cE :" + cE.toString();
		} finally {

			if (conn != null) {
				cManager.releaseConnection(conn);
			}
		}
		return RESULTS;
	}

	public String getCoursesHeadquarters() {

		ConnectionManager cManager = null;
		Connection conn = null;

		String RESULTS = "";
		StringBuilder TableData = new StringBuilder("");
		try {

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();
			
			TableData.append(" SELECT DISTINCT NAME FROM MI_NODE WHERE PARENT_PK1 IN (SELECT MN.PK1 FROM MI_NODE MN WHERE MN.PARENT_PK1 = 1)");
			
			ResultSet rSet = conn.createStatement().executeQuery(TableData.toString());

			RESULTS = "<select name='Headquarters' id='Headquarters'>";
			RESULTS += "<option value='Todo' selected>Todo</option>";

			while (rSet.next()) {
				String value = rSet.getString(1);
				String Name = value;

				RESULTS += "<option style=\"margin:0 10px 0 10px;\" value='" + value + "'>" + Name + "</option>";
			}

			RESULTS += "</select>";

			rSet.close();


		} catch (java.sql.SQLException sE) {

			RESULTS += "java.sql.SQLException sE: " + sE.toString();
			System.out.println("HeadQuarters Query: " + TableData.toString());
			sE.printStackTrace();

		} catch (ConnectionNotAvailableException cE) {

			RESULTS += "ConnectionNotAvailableException cE :" + cE.toString();
		} finally {

			if (conn != null) {
				cManager.releaseConnection(conn);
			}
		}
		return RESULTS;
	}

	
	// Send Email to Students with the Specific Format
	public String SendEmailtoStudents(String[] Emails, String Message, String[] Names, String[] Ids, String[] Status, boolean test) throws InstantiationException, IllegalAccessException, ConnectionNotAvailableException, UnsupportedEncodingException, MessagingException, ValidationException, SQLException {

		String Results = "";

		BbMail mail;

			String FormatText = Message;

			mail = BbMailManagerFactory.getInstance().createMessage();;

			ConnectionManager cManager = null;
			Connection conn = null;
			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			for (int i = 0; i < Emails.length; i++) {
				mail.setFrom("Monitoring.Tool@blackboard.com");
				mail.setSubject("Acceso Semipresencial AIEP");
				mail.setBody("<html>" + FormatText.replace("[nombre_estudiante]", Names[i]) + "</html>");
				System.out.println("Sending Email for " + Names[i]);
				if(test) {
					mail.setTo("cesar.bonilla@laureate.net");
				} else 
					mail.setTo(Emails[i]);
				mail.send();

				Message = Status[i];

				Results += RegisterManagement(Ids[i], "", Message, "EMAIL",
						FormatText.replace("[nombre_estudiante]", Names[i]), "C", " ", "Automatic", conn, "false");
			}

			conn.close();
			cManager.close();
		return Results;
	}

	// Insert Management into table
	public String RegisterManagement(String pk1, String dtcreated, String motive, String channel, String observations,
			String contact_type, String student_response, String RegistrationType, Connection conn, String Edit) {

		String RESULTS = "Entro RegisterManagement ";

		try {
			if (RegistrationType.equals("Automatic")) {

				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String[] Temp = formatter.format(new Date()).split("/");
				String DateSent = Temp[2] + "/" + Temp[1] + "/" + Temp[0];
				dtcreated = DateSent;
			}

			String Query = "";

			if (Edit.equals("true")) {

				StringBuilder QueryData = new StringBuilder();

				QueryData.append("UPDATE LNOH_STUDENT_MANAGEMENT ");
				QueryData.append("SET ");
				QueryData.append("DTCREATED = (SELECT to_date('" + dtcreated + "', 'yyyy-mm-dd') FROM DUAL),");
				QueryData.append("MOTIVE = '" + motive + "',");
				QueryData.append("CHANNEL = '" + channel + "',");
				QueryData.append("OBSERVATIONS = '" + observations + "',");
				QueryData.append("CONTACT_TYPE = '" + contact_type + "',");
				QueryData.append("STUDENT_RESPONSE = '" + student_response + "' ");
				QueryData.append("WHERE ID = " + pk1);

				Query = QueryData.toString();

				if (motive.equals("Nunca Conectado")) {

					motive = "<label id='StatusText'>" + motive + "</label>";
					motive = "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/rojo.png' style='height: 25px;' alt='Nunca Conectado' title='Nunca Conectado'>â€‹"
							+ motive;
				}
				if (motive.equals("Conectado durante la Última semana")) {

					motive = "<label id='StatusText'>" + motive + "</label>";
					motive = "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/verde.png' style='height: 25px;' alt='Conectado durante la Ãºltima semana' title='Conectado durante la Ãºltima semana'>â€‹"
							+ motive;
				}
				if (motive.equals("Más de 1 semana sin conexión")) {

					motive = "<label id='StatusText'>" + motive + "</label>";
					motive = "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/amarillo.png' style='height: 25px;' alt='Más de 1 semana sin conexión' title='Más de 1 semana sin conexión'>"
							+ motive;
				}
				if (motive.equals("Más de 2 semanas sin conexión")) {

					motive = "<label id='StatusText'>" + motive + "</label>";
					motive = "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/naranja.png' style='height: 25px;' alt='Más de 2 semanas sin conexión' title='Más de 2 semanas sin conexión'>"
							+ motive;
				}

				if (contact_type.equals("C")) {

					RESULTS = "<table id='TableH" + pk1 + "' width='99%' border='0' class='tablaHistC' align='center'>"
							+ "  <tbody>" + "    <tr>" + "      <th width='15%'>Fecha de Gestión : </th>"
							+ "      <td id='DateSent" + pk1 + "' width='13%'>" + dtcreated + "</td>"
							+ "      <th width='15%'>Motivo Contacto : </th>" + "      <td id='Status" + pk1
							+ "' width='23%'>" + motive + "</td>" + "      <th width='15%'>Vía de Contacto : </th>"
							+ "      <td id='Channel" + pk1 + "' width='14%'>" + channel + "</td>"
							+ "      <td width='6%'><img id='Edit" + pk1
							+ "' title='C' src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/pencil.png'></td>" + "    </tr>"
							+ "    <tr>" + "      <th>Observaciones : </th>" + "      <td id='Observations" + pk1
							+ "' colspan='6'>" + observations + "</td>" + "    </tr>" + "  </tbody>" + "</table>"
							+ "<br>";
				} else {

					RESULTS = "<table id='TableH" + pk1 + "' width='99%' border='0' class='tablaHistE' align='center'>"
							+ "  <tbody>" + "    <tr>" + "      <th width='15%'>Fecha de Gestión : </th>"
							+ "      <td id='DateSent" + pk1 + "' width='13%'>" + dtcreated + "</td>"
							+ "      <th width='15%'>Motivo Contacto : </th>" + "      <td id='Status" + pk1
							+ "' width='23%'>" + motive + "</td>" + "      <th width='15%'>Vía de Contacto : </th>"
							+ "      <td id='Channel" + pk1 + "' width='14%'>" + channel + "</td>"
							+ "      <td width='6%'><img id='Edit" + pk1
							+ "' title='E' src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/pencil.png'></td>" + "    </tr>"
							+ "    <tr>" + "      <th>Resp. Estudiante : </th>" + "      <td id='StudentResponse" + pk1
							+ "' colspan='6'>" + student_response + "</td>" + "    </tr>" + "    <tr>"
							+ "      <th>Observaciones : </th>" + "      <td id='Observations" + pk1 + "' colspan='6'>"
							+ observations + "</td>" + "    </tr>" + "  </tbody>" + "</table>" + "<br>";
				}
			} else {

				Query = "INSERT INTO LNOH_STUDENT_MANAGEMENT VALUES (((SELECT COUNT(*) FROM LNOH_STUDENT_MANAGEMENT) + 1),"
						+ pk1 + ",TO_DATE('" + dtcreated + "', 'YYYY-MM-DD'),'" + motive + "','" + channel + "','"
						+ observations + "','" + contact_type + "','" + student_response + "')";
			}

			Statement st = conn.createStatement();
			st.executeUpdate(Query);

			st.close();

		} catch (SQLException e) {
			e.printStackTrace();
			RESULTS = "Error: " + e.getLocalizedMessage();
		}

		return RESULTS;
	}

	// CONTROL DE ESTUDIANTES SP
	public String getStudentRecord(String pk1) {

		String HTMLData = "";

		ConnectionManager cManager = null;
		Connection conn = null;
		PreparedStatement selectQuery = null;

		try {

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			selectQuery = conn.prepareStatement(
					"SELECT * from LNOH_STUDENT_MANAGEMENT SM WHERE SM.PK1 = " + pk1 + " ORDER BY DTCREATED",
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			ResultSet rSet = selectQuery.executeQuery();

			String DtCreated = "";
			String Motive = "";
			String Channel = "";
			String Observations = "";
			String Contact_Type = "";
			String Student_Response = "";
			String[] Temp = null;
			String Id = "";

			while (rSet.next()) {

				DtCreated = rSet.getString(3).split(" ")[0];
				Motive = rSet.getString(4);
				Channel = rSet.getString(5);
				Observations = rSet.getString(6);
				Contact_Type = rSet.getString(7);
				Student_Response = rSet.getString(8);

				Temp = DtCreated.split("-");
				DtCreated = Temp[0] + "/" + Temp[1] + "/" + Temp[2];

				Id = rSet.getString(1);

				if (Motive.equals("Nunca Conectado")) {

					Motive = "<label id='StatusText'>" + Motive + "</label>";
					Motive = "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/rojo.png' style='height: 35%;' alt='Nunca Conectado' title='Nunca Conectado'>â€‹"
							+ Motive;
				}
				if (Motive.equals("Conectado durante la última semana")) {

					Motive = "<label id='StatusText'>" + Motive + "</label>";
					Motive = "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/verde.png' style='height: 35%;' alt='Conectado durante la última semana' title='Conectado durante la última semana'>â€‹"
							+ Motive;
				}
				if (Motive.equals("Más de 1 semana sin conexión")) {

					Motive = "<label id='StatusText'>" + Motive + "</label>";
					Motive = "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/amarillo.png' style='height: 35%;' alt='Más de 1 semana sin conexión' title='Más de 1 semana sin conexión'>"
							+ Motive;
				}
				if (Motive.equals("Más de 2 semanas sin conexión")) {

					Motive = "<label id='StatusText'>" + Motive + "</label>";
					Motive = "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/naranja.png' style='height: 35%;' alt='Más de 2 semanas sin conexión' title='Más de 2 semanas sin conexión'>"
							+ Motive;
				}

				if (Contact_Type.equals("C")) {

					HTMLData += "<table id=\"TableH" + Id
							+ "\" width=\"99%\" border=\"0\" class=\"tablaHistC\" align=\"center\">" + "  <tbody>"
							+ "    <tr>" + "      <th width=\"15%\">Fecha de Gestión : </th>"
							+ "      <td id=\"DateSent" + Id + "\" width=\"13%\">" + DtCreated + "</td>"
							+ "      <th width=\"15%\">Motivo Contacto : </th>" + "      <td id=\"Status" + Id
							+ "\" width=\"23%\">" + Motive + "</td>" + "      <th width=\"15%\">Vía de Contacto : </th>"
							+ "      <td id=\"Channel" + Id + "\" width=\"14%\">" + Channel + "</td>"
							+ "      <td width=\"6%\"><img id='Edit" + Id
							+ "' title='C' style='height: 130%;' src=\"/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/pencil.png\"></td>"
							+ "    </tr>" + "    <tr>" + "      <th>Observaciones : </th>"
							+ "      <td id=\"Observations" + Id + "\" colspan=\"6\">" + Observations + "</td>"
							+ "    </tr>" + "  </tbody>" + "</table>" + "<br>";
				} else {

					HTMLData += "<table id=\"TableH" + Id
							+ "\" width=\"99%\" border=\"0\" class=\"tablaHistE\" align=\"center\">" + "  <tbody>"
							+ "    <tr>" + "      <th width=\"15%\">Fecha de Gestión : </th>"
							+ "      <td id=\"DateSent" + Id + "\" width=\"13%\">" + DtCreated + "</td>"
							+ "      <th width=\"15%\">Motivo Contacto : </th>" + "      <td id=\"Status" + Id
							+ "\" width=\"23%\">" + Motive + "</td>" + "      <th width=\"15%\">Vía de Contacto : </th>"
							+ "      <td id=\"Channel" + Id + "\" width=\"14%\">" + Channel + "</td>"
							+ "      <td width=\"6%\"><img id='Edit" + Id
							+ "' title='E' style'height: 130%;' src=\"/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/pencil.png\"></td>"
							+ "    </tr>" + "    <tr>" + "      <th>Resp. Estudiante : </th>"
							+ "      <td id=\"StudentResponse" + Id + "\" colspan=\"6\">" + Student_Response + "</td>"
							+ "    </tr>" + "    <tr>" + "      <th>Observaciones : </th>"
							+ "      <td id=\"Observations" + Id + "\" colspan=\"6\">" + Observations + "</td>"
							+ "    </tr>" + "  </tbody>" + "</table>" + "<br>";
				}
			}

		} catch (ConnectionNotAvailableException e) {
			HTMLData += "Error: " + e.getLocalizedMessage();
		} catch (SQLException e) {
			HTMLData += "Error: " + e.getLocalizedMessage();
		}

		return HTMLData;
	}

	public String GetManagementXls(String Headquarter, String modalidad) {

		String Results = "Results";

		ConnectionManager cManager = null;
		Connection conn = null;
		StringBuilder Data = new StringBuilder();

		try {
			Data.append("<table border=1>");
			Data.append("<tr>");
			Data.append("<td>MODALIDAD</td>");
			Data.append("<td>SEDE</td>");
			Data.append("<td>USER NAME</td>");
			Data.append("<td>RUT</td>");
			Data.append("<td>APELLIDOS</td>");
			Data.append("<td>NOMBRES</td>");
			Data.append("<td>EMAIL</td>");

			Data.append("<td>ULTIMO INGRESO</td>");
			Data.append("<td>GESTION COORDINADOR</td>");
			Data.append("<td>RESPUESTAS ESTUDIANTES</td>");
			Data.append("<td>CONTACTAR</td>");
			Data.append("</tr>");

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();
			
			StringBuilder TableData = new StringBuilder();

			TableData.append("SELECT US.PK1, US.LASTNAME, US.FIRSTNAME, US.STUDENT_ID, US.USER_ID,"
					+ " US.EMAIL, US.LAST_LOGIN_DATE, (systimestamp - US.LAST_LOGIN_DATE) \"Date Diff\" , IR.ROLE_ID, US.B_PHONE_1"
					+ " FROM USERS US INNER JOIN INSTITUTION_ROLES IR ON US.INSTITUTION_ROLES_PK1=IR.PK1 ");
			if (Headquarter.equals("Todo") && modalidad.equals("Todo")) {
				TableData.append("WHERE IR.ROLE_ID IN ('Online','Semipresencial','Presencial') ");
			} else if (Headquarter.equals("Todo")) {
				TableData.append("WHERE US.INSTITUTION_ROLES_PK1=" + modalidad + " ");
			} else if (modalidad.equals("Todo")) {
				TableData.append("WHERE IR.ROLE_ID IN ('Online','Semipresencial','Presencial') ");
				TableData.append("AND US.B_PHONE_1 ='" + Headquarter + "' ");
			} else {
				TableData.append("WHERE US.INSTITUTION_ROLES_PK1=" + modalidad + " ");
				TableData.append("AND US.B_PHONE_1= '" + Headquarter + "' ");
			}
			TableData.append("AND US.SYSTEM_ROLE = 'N' ");
			TableData.append("AND US.DATA_SRC_PK1 != 2 ");
			TableData.append(" ORDER BY LAST_LOGIN_DATE ASC ");
			System.out.println("XLS EXPORT QUERY: " + TableData.toString()) ;
			ResultSet rSet = conn.createStatement().executeQuery(TableData.toString());
			ResultSet rSet2 = null;
			
			String Modalidad = "";
			String userName = "";
			String RUT = "";
			String pk1 = "";
			String LastName = "";
			String FirstName = "";
			String Email = "";
			String Sede = "";
			String Last_Access = "";
			String Coordinator = "";
			String Student = "";
			String NoContact = "";

			int CountC = 0;
			int CountE = 0;
			String VerifyNoContact = "-";

			while (rSet.next()) {
				Modalidad = rSet.getString("ROLE_ID");
				userName = rSet.getString("USER_ID");
				RUT = rSet.getString("STUDENT_ID");
				pk1 = rSet.getString("PK1");
				LastName = rSet.getString("LASTNAME");
				FirstName = rSet.getString("FIRSTNAME");
				Email = rSet.getString("EMAIL");
				Sede = rSet.getString("B_PHONE_1");
				Last_Access = rSet.getString("LAST_LOGIN_DATE");
				
				Statement st = conn.createStatement();
				
				rSet2 = st.executeQuery("SELECT SM.CONTACT_TYPE, SM.STUDENT_RESPONSE FROM LNOH_STUDENT_MANAGEMENT SM WHERE SM.PK1 = '"
						+ pk1 + "'");

				while (rSet2.next()) {

					if (rSet2.getString(1).equals("C")) {

						CountC++;
					} else {

						CountE++;
					}

					if (rSet2.getString(2).equals("NO CONTACTAR")) {
						VerifyNoContact = "NO CONTACTAR";
					}

				}
				rSet2.close();
				rSet2 = null;
				st.close();
				st = null;

				Coordinator = CountC + "";
				Student = CountE + "";
				NoContact = VerifyNoContact;

				if (Last_Access != null) {

					Last_Access = Last_Access.split(" ")[0];
				} else {

					Last_Access = "NUNCA";
				}

				if (Sede == null) {

					Sede = "-";
				}

				Data.append("<tr>");
				Data.append("<td>"+Modalidad+"</td>");
				Data.append("<td>"+Sede+"</td>");
				Data.append("<td>"+userName+"</td>");
				Data.append("<td>"+RUT+"</td>");
				Data.append("<td>"+LastName+"</td>");
				Data.append("<td>"+FirstName+"</td>");
				Data.append("<td>"+Email+"</td>");
				Data.append("<td>"+Last_Access+"</td>");
				Data.append("<td>"+Coordinator+"</td>");
				Data.append("<td>"+Student+"</td>");
				Data.append("<td>"+NoContact+"</td>");
				Data.append("</tr>");

				CountC = 0;
				CountE = 0;
				VerifyNoContact = "-";
			}

			rSet.close();

		} catch (ConnectionNotAvailableException e) {
			System.out.print(e.getFullMessageTrace());
			Results += "Error: " + e.getLocalizedMessage();
			//e.printStackTrace();
		} catch (SQLException e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
			//e.printStackTrace();
			Results += "Error: " + e.getLocalizedMessage();
		}
		Data.append("</table>");
		Results = Data.toString();
		return Results;
	}

	public String GetRecordXls(String pk1) {

		String Results = "";
		StringBuilder Data = new StringBuilder();

		try {

			ConnectionManager cManager = null;
			Connection conn = null;
			PreparedStatement selectQuery = null;

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			Data.append("<table>");
			Data.append("<tr>");
			Data.append("<td>");
			Data.append("APELLIDOS");
			Data.append("</td>");
			Data.append("<td>");
			Data.append("NOMBRES");
			Data.append("</td>");
			Data.append("<td>");
			Data.append("RUT");
			Data.append("</td>");
			Data.append("<td>");
			Data.append("EMAIL");
			Data.append("</td>");
			Data.append("<td>");
			Data.append("TELEFONO");
			Data.append("</td>");
			Data.append("<td>");
			Data.append("ESCUELA");
			Data.append("</td>");
			Data.append("<td>");
			Data.append("FECHA");
			Data.append("</td>");
			Data.append("<td>");
			Data.append("VIA DE CONTACTO");
			Data.append("</td>");
			Data.append("<td>");
			Data.append("MOTIVO");
			Data.append("</td>");
			Data.append("<td>");
			Data.append("GESTION");
			Data.append("</td>");
			Data.append("</tr>");

			StringBuilder TableData = new StringBuilder();

			TableData.append(
					"SELECT US.LASTNAME, US.FIRSTNAME, US.USER_ID, US.EMAIL, US.M_PHONE, SM.DTCREATED, SM.CHANNEL,SM.MOTIVE,SM.OBSERVATIONS FROM USERS US ");
			TableData.append("INNER JOIN LNOH_STUDENT_MANAGEMENT SM ON SM.PK1 = US.PK1 ");
			TableData.append("AND US.PK1 = " + pk1);
			TableData.append("ORDER BY SM.DTCREATED ASC ");
			
			

			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rSet = selectQuery.executeQuery();

			String LastName = "";
			String FirstName = "";
			String User_Id = "";
			String Email = "";
			String Phone = "";
			String DtCreated = "";
			String Channel = "";
			String Motive = "";
			String Observations = "";

			while (rSet.next()) {

				LastName = rSet.getString(1);
				FirstName = rSet.getString(2);
				User_Id = rSet.getString(3);
				Email = rSet.getString(4);
				Phone = rSet.getString(5);
				DtCreated = rSet.getString(6).split(" ")[0];
				Channel = rSet.getString(7);
				Motive = rSet.getString(8);
				Observations = rSet.getString(9);

				if (Phone == null) {

					Phone = "-";
				}

				Data.append("<tr>");
				Data.append("<td>");
				Data.append(LastName);
				Data.append("</td>");
				Data.append("<td>");
				Data.append(FirstName);
				Data.append("</td>");
				Data.append("<td>");
				Data.append(User_Id);
				Data.append("</td>");
				Data.append("<td>");
				Data.append(Email);
				Data.append("</td>");
				Data.append("<td>");
				Data.append(Phone);
				Data.append("</td>");
				Data.append("<td>");
				Data.append("-");
				Data.append("</td>");
				Data.append("<td>");
				Data.append(DtCreated);
				Data.append("</td>");
				Data.append("<td>");
				Data.append(Channel);
				Data.append("</td>");
				Data.append("<td>");
				Data.append(Motive);
				Data.append("</td>");
				Data.append("<td>");
				Data.append(Observations);
				Data.append("</td>");
				Data.append("</tr>");
			}
			
			Data.append("</table>");
			Results = Data.toString();

		} catch (ConnectionNotAvailableException e) {
			Results += "Error: " + e.getLocalizedMessage();
		} catch (SQLException e) {
			Results += "Error: " + e.getLocalizedMessage();
		}

		return Results;
	}

	public String getCourses(int pageNumber) {

		ConnectionManager cManager = null;
		Connection conn = null;
		PreparedStatement selectQuery = null;

		String RESULTS = "";
		
		int firstRow = (pageNumber - 1) * pageSize + 1;
		int lastRow = (pageNumber * pageSize) + 1;

		try {

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			StringBuilder TableData = new StringBuilder();

			TableData.append("SELECT * FROM ( SELECT a.*, rownum rowcount FROM (");
			TableData.append(
					"SELECT CM.PK1, CM.COURSE_ID, CM.COURSE_NAME, CONCAT(CONCAT(US.FIRSTNAME,' '),US.LASTNAME) \"DOCENTE\" , CM.START_DATE \"FECHA INICIAL\",CM.END_DATE \"FECHA FINAL\", ");
			TableData.append("CASE ");
			TableData.append("WHEN (CM.END_DATE - systimestamp) >  (systimestamp - systimestamp) THEN 'EN EJECUCION' ");
			TableData.append("ELSE 'EJECUTADO' ");
			TableData.append("END \"STATUS\"  FROM COURSE_MAIN CM ");
			TableData.append("INNER JOIN COURSE_USERS CU ON CU.CRSMAIN_PK1 = CM.PK1 ");
			TableData.append("INNER JOIN USERS US ON US.PK1 = CU.USERS_PK1 ");
			TableData.append("INNER JOIN INSTITUTION_ROLES IR ON US.INSTITUTION_ROLES_PK1 = IR.PK1 ");
			TableData.append("WHERE CU.ROLE = 'Docente' ");
			TableData.append("AND CM.ROW_STATUS = 0 ");
			TableData.append(
					"AND NVL(CM.END_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) != to_date('0001/01/01', 'yyyy-mm-dd') ");
			TableData.append("ORDER BY CM.COURSE_NAME) a  ");
			TableData.append("WHERE rownum < " + lastRow + " ");
			TableData.append(") WHERE rowcount >= " + firstRow);

			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rSet = selectQuery.executeQuery();

			RESULTS = "<table id='grid-basic' class='table table-condensed table-hover table-striped'>";
			RESULTS += "<thead>";
			RESULTS += "<tr>";

			String ColumnName = "";

			ColumnName = "COD";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "NOMBRE MODULO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "DOCENTE";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "FECHA INICIAL";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "FECHA FINAL";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			RESULTS += "</tr>";
			RESULTS += "</thead>";
			RESULTS += "<tbody>";

			String pk1 = "";
			String Course_Id = "";
			String Course_Name = "";
			String Instructor = "";
			String Start_Date = "";
			String End_Date = "";
			String Status = "";
			String BGColor = "";

			long count = 0L;

			while (rSet.next()) {

				pk1 = rSet.getString(1);
				Course_Id = rSet.getString(2);
				Course_Name = rSet.getString(3);
				Instructor = rSet.getString(4);
				Start_Date = rSet.getString(5);
				End_Date = rSet.getString(6);
				Status = rSet.getString(7);
				BGColor = "";

				if (Start_Date != null) {

					Start_Date = Start_Date.split(" ")[0];
				} else {

					Start_Date = "-";
				}
				if (End_Date != null) {

					End_Date = End_Date.split(" ")[0];
				} else {

					End_Date = "-";
				}

				if (Status.equals("EJECUTADO")) {

					BGColor = "style='background-color: indianred;'";
				}

				RESULTS += "<tr>";
				RESULTS += "<td><a target='_blank' href='/webapps/lnoh-AIEPMTOOL-BBLEARN/app/StudentActivity?Id=" + pk1
						+ "&Name=" + Course_Name + "&Status=" + Status + "'>" + Course_Id + "</a></td>";
				RESULTS += "<td>" + Course_Name + "</td>";
				RESULTS += "<td>" + Instructor + "</td>";
				RESULTS += "<td>" + Start_Date + "</td>";
				RESULTS += "<td " + BGColor + ">" + End_Date + "</td>";
				RESULTS += "</tr>";

				count++;
			}

			RESULTS += "</tbody>";
			RESULTS += "</table>";
			//RESULTS += "<label>Total: " + count + "</label>";

			rSet.close();

			selectQuery.close();

		} catch (java.sql.SQLException sE) {

			RESULTS += "java.sql.SQLException sE: " + sE.toString();

		} catch (ConnectionNotAvailableException cE) {

			RESULTS += "ConnectionNotAvailableException cE :" + cE.toString();
		} finally {

			if (conn != null) {
				cManager.releaseConnection(conn);
			}
		}

		return RESULTS;
	}

	public String getCourses(String Headquarter,int currentPage,String Modalidad,String CourseFilter) {

		ConnectionManager cManager = null;
		Connection conn = null;
		PreparedStatement selectQuery = null;

		String RESULTS = "";
		
		boolean IsFirstPage = false;
		
		if(currentPage == -1){
			
			IsFirstPage = true;
			currentPage = 1;
		}
		
		int firstRow = (currentPage - 1) * pageSize + 1;
		int lastRow = (currentPage * pageSize) + 1;

		try {

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			StringBuilder TableData = new StringBuilder();

			TableData.append("SELECT * FROM ( SELECT a.*, rownum rowcount FROM (");
			TableData.append("SELECT CM.PK1, CM.COURSE_ID, CM.COURSE_NAME, CONCAT(CONCAT(US.FIRSTNAME,' '),US.LASTNAME) \"DOCENTE\" , CM.START_DATE \"FECHA INICIAL\",CM.END_DATE \"FECHA FINAL\", ");
			TableData.append("CASE ");
			TableData.append("WHEN (CM.END_DATE - systimestamp) >  (systimestamp - systimestamp) THEN 'EN EJECUCION' ");
			TableData.append("ELSE 'EJECUTADO' ");
			TableData.append("END \"STATUS\"  FROM COURSE_MAIN CM ");
			TableData.append("INNER JOIN COURSE_USERS CU ON CU.CRSMAIN_PK1 = CM.PK1 ");
			TableData.append("INNER JOIN USERS US ON US.PK1 = CU.USERS_PK1 ");
			TableData.append("INNER JOIN INSTITUTION_ROLES IR ON US.INSTITUTION_ROLES_PK1 = IR.PK1 ");
			TableData.append("WHERE CU.ROLE = 'Docente' ");
			TableData.append("AND CM.ROW_STATUS = 0 AND CU.ROW_STATUS = 0");
			
			if (!CourseFilter.equals("Todo")){
				
				TableData.append("AND CM.COURSE_ID LIKE '"+ CourseFilter +"' ");
			}

			if (!Headquarter.equals("Todo")) {

				TableData.append(" AND CM.COURSE_ID IN ( select course_id from domain_course_coll_uid dom_crs inner join ");
				TableData.append(" course_main course on course.pk1 = dom_crs.course_main_pk1 INNER JOIN ");
				TableData.append(" domain on domain.pk1 = dom_crs.domain_pk1 ");
				TableData.append(" inner join mi_node modulo  on modulo.domain_pk1 = domain.pk1 inner join ");
				TableData.append(" mi_node semestre on semestre.pk1 = modulo.parent_pk1 inner join ");
				TableData.append(" mi_node anio on anio.pk1 = semestre.parent_pk1 inner join ");
				TableData.append(" mi_node carrera on carrera.pk1 = anio.parent_pk1 inner join ");
				TableData.append(" mi_node escuela on escuela.pk1 = carrera.parent_pk1 inner join ");
				TableData.append(" mi_node sede on sede.pk1 = escuela.parent_pk1 ");
				TableData.append(" where sede.name = '"+Headquarter+"' ) ");
			}
			
			TableData.append("AND NVL(CM.END_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) != to_date('0001/01/01', 'yyyy-mm-dd') ");
			TableData.append("ORDER BY CM.COURSE_NAME) a  ");
			TableData.append("WHERE rownum < " + lastRow + " ");
			TableData.append(") WHERE rowcount >= " + firstRow);

			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rSet = selectQuery.executeQuery();
			
			RESULTS += "<div id='CourseData'>";
			RESULTS += "<table id='grid-basic' class='table table-condensed table-hover table-striped'>";
			RESULTS += "<thead>";
			RESULTS += "<tr>";

			String ColumnName = "";

			ColumnName = "COD";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "NOMBRE MODULO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "DOCENTE";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "FECHA INICIAL";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "FECHA FINAL";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			RESULTS += "</tr>";
			RESULTS += "</thead>";
			RESULTS += "<tbody>";

			String pk1 = "";
			String Course_Id = "";
			String Course_Name = "";
			String Instructor = "";
			String Start_Date = "";
			String End_Date = "";
			String Status = "";
			String BGColor = "";

			while (rSet.next()) {

				pk1 = rSet.getString(1);
				Course_Id = rSet.getString(2);
				Course_Name = rSet.getString(3);
				Instructor = rSet.getString(4);
				Start_Date = rSet.getString(5);
				End_Date = rSet.getString(6);
				Status = rSet.getString(7);
				BGColor = "";

				if (Start_Date != null) {

					Start_Date = Start_Date.split(" ")[0];
				} else {

					Start_Date = "-";
				}
				if (End_Date != null) {

					End_Date = End_Date.split(" ")[0];
				} else {

					End_Date = "-";
				}

				if (Status.equals("EJECUTADO")) {

					BGColor = "style='background-color: indianred;'";
				}

				RESULTS += "<tr>";
				RESULTS += "<td><a target='_blank' href='/webapps/lnoh-AIEPMTOOL-BBLEARN/app/StudentActivity?Id=" + pk1
						+ "&Name=" + Course_Name + "&Status=" + Status + "'>" + Course_Id + "</a></td>";
				RESULTS += "<td>" + Course_Name + "</td>";
				RESULTS += "<td>" + Instructor + "</td>";
				RESULTS += "<td>" + Start_Date + "</td>";
				RESULTS += "<td " + BGColor + ">" + End_Date + "</td>";
				RESULTS += "</tr>";
			}

			RESULTS += "</tbody>";
			RESULTS += "</table>";
			RESULTS += "</div>";
			//RESULTS += "<label>Total: " + count + "</label>";
			
			rSet.close();

			selectQuery.close();

			if(IsFirstPage){
				
				int count = 0;
				
				TableData = new StringBuilder();

				TableData.append("SELECT COUNT(*) FROM (");
				TableData.append("SELECT CM.PK1, CM.COURSE_ID, CM.COURSE_NAME, CONCAT(CONCAT(US.FIRSTNAME,' '),US.LASTNAME) \"DOCENTE\" , CM.START_DATE \"FECHA INICIAL\",CM.END_DATE \"FECHA FINAL\", ");
				TableData.append("CASE ");
				TableData.append("WHEN (CM.END_DATE - systimestamp) >  (systimestamp - systimestamp) THEN 'EN EJECUCION' ");
				TableData.append("ELSE 'EJECUTADO' ");
				TableData.append("END \"STATUS\"  FROM COURSE_MAIN CM ");
				TableData.append("INNER JOIN COURSE_USERS CU ON CU.CRSMAIN_PK1 = CM.PK1 ");
				TableData.append("INNER JOIN USERS US ON US.PK1 = CU.USERS_PK1 ");
				TableData.append("INNER JOIN INSTITUTION_ROLES IR ON US.INSTITUTION_ROLES_PK1 = IR.PK1 ");
				TableData.append("WHERE CU.ROLE = 'Docente' ");
				TableData.append("AND CM.ROW_STATUS = 0 AND CU.ROW_STATUS = 0 ");

				if (!CourseFilter.equals("Todo")){
					
					TableData.append("AND CM.COURSE_ID LIKE '"+ CourseFilter +"' ");
				}

				if (!Headquarter.equals("Todo")) {

					TableData.append("AND US.B_PHONE_1 = '" + Headquarter + "' ");
				}

				TableData.append("AND NVL(CM.END_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) != to_date('0001/01/01', 'yyyy-mm-dd') ");
				TableData.append("ORDER BY CM.COURSE_NAME) Results  ");

				selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
						ResultSet.CONCUR_READ_ONLY);

				rSet = selectQuery.executeQuery();
				
				while(rSet.next()){
					
					count = rSet.getInt(1);
				}
				
				int MaxPages = (int) Math.ceil((double)count / this.pageSize);
				
				StringBuilder PaginationData = new StringBuilder();
				PaginationData.append("<div id='tablePagination' class='tablePagination' style='float: right;'>");
				PaginationData.append("<input id='pageNumber' type='number' value='1' min='1' max='"+ MaxPages +"'>");
				PaginationData.append("<label> de "+ MaxPages +" paginas.</label> ");
				PaginationData.append("<button class='button' onclick='BtnFetchInfo(pageNumber.value)'>Ir</button>");
				PaginationData.append("</div>");
				
				RESULTS = PaginationData.toString() + RESULTS;
			}
			
		} catch (java.sql.SQLException sE) {

			RESULTS += "java.sql.SQLException sE: " + sE.toString();

		} catch (ConnectionNotAvailableException cE) {

			RESULTS += "ConnectionNotAvailableException cE :" + cE.toString();
		} finally {

			if (conn != null) {
				cManager.releaseConnection(conn);
			}
		}

		return RESULTS;
	}

	
	public String getStudentActivity(String Id) {

		ConnectionManager cManager = null;
		Connection conn = null;
		PreparedStatement selectQuery = null;

		String RESULTS = "";
		
		StringBuilder ReportData = new StringBuilder();

		try {

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			StringBuilder TableData = new StringBuilder();
			TableData.append("SELECT US.PK1, US.LASTNAME, US.FIRSTNAME, US.STUDENT_ID, CU.LAST_ACCESS_DATE, US.EMAIL FROM COURSE_USERS CU ");
			TableData.append("INNER JOIN COURSE_MAIN CM ON CM.PK1 = CU.CRSMAIN_PK1 ");
			TableData.append("INNER JOIN USERS US ON US.PK1 = CU.USERS_PK1 ");
			TableData.append("WHERE CU.ROLE = 'S' ");
			TableData.append("AND CM.PK1 = " + Id + " ");
			TableData.append("ORDER BY US.LASTNAME");

			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rSet = selectQuery.executeQuery();

			RESULTS = "<table id='grid-basic' class='table table-condensed table-hover table-striped'>";
			RESULTS += "<thead>";
			RESULTS += "<tr>";

			String ColumnName = "";

			ColumnName = "APELLIDO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "NOMBRES";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "RUT";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";
			
			ColumnName = "ULTIMO INGRESO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			String pk1 = "";
			String LastName = "";
			String FirstName = "";
			String User_Id = "";
			String Email = "";
			String Contact = "";
			String History = "";
			String LastAccess = "";

			ArrayList<String> StudentIds = new ArrayList<String>();
			ArrayList<String> TempInfo = null;
			ArrayList<ArrayList<String>> TableInfo = new ArrayList<ArrayList<String>>();

			// GET STUDENTS INFO
			while (rSet.next()) {

				StudentIds.add(rSet.getString(1));
				LastName = rSet.getString(2);
				FirstName = rSet.getString(3);
				User_Id = rSet.getString(4);
				LastAccess = rSet.getString(5);
				Email = rSet.getString(6);

				if (LastAccess == null) {

					LastAccess = "NUNCA";
				} else {

					LastAccess = LastAccess.split(" ")[0];
				}

				//Email = "reynaldo.narvaez@laureate.net";
				Contact = "<input id='Chk" + rSet.getString(1) + "' type='checkbox' name='chk' value='" + Email
						+ "' style='display: none;'><img id='Management" + rSet.getString(1)
						+ "' src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/registra.png' style='height: 20px;'><div id='Email"
						+ rSet.getString(1) + "' style='display:none;'>" + Email + "</div>";

				History = "<img id='History" + rSet.getString(1)
						+ "' src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/historial.png' style='height: 20px;'><div id='LastAccess"
						+ rSet.getString(1) + "' style='display:none;'>" + LastAccess + "</div>";

				TempInfo = new ArrayList<String>();
				TempInfo.add(LastName);
				TempInfo.add(FirstName);
				TempInfo.add(User_Id);
				TempInfo.add(LastAccess);
				TempInfo.add(Contact);
				TempInfo.add(History);

				TableInfo.add(TempInfo);

			}

			rSet.close();

			// GET COURSE AREAS
			TableData = new StringBuilder();
			TableData.append("SELECT * FROM( ");
			TableData.append("	SELECT CT.COURSE_CONTENTS_PK1, CT.LABEL, CT.POSITION, COUNT(*) FROM COURSE_MAIN CM ");
			TableData.append("	INNER JOIN COURSE_TOC CT ON CT.CRSMAIN_PK1 = CM.PK1 ");
			TableData.append("	INNER JOIN COURSE_CONTENTS CC ON CC.PARENT_PK1 = CT.COURSE_CONTENTS_PK1 ");
			TableData.append("	WHERE CM.PK1 = " + Id + " ");
			TableData.append("	AND CT.TARGET_TYPE = 'CONTENT_LINK' ");
			TableData.append(
					"	AND CC.CNTHNDLR_HANDLE IN ('resource/x-bb-forumlink','resource/x-bb-assignment','resource/x-bb-asmt-test-link') ");
			TableData.append("	GROUP BY CT.COURSE_CONTENTS_PK1, CT.LABEL, CT.POSITION ");
			TableData.append(") CTT ORDER BY CTT.POSITION ASC ");

			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			rSet = selectQuery.executeQuery();

			String CCPk1s = "";
			String Course_Area_Title = "";

			ArrayList<String> Course_Contents_Pk1 = new ArrayList<String>();

			String Options = "";

			while (rSet.next()) {

				CCPk1s = rSet.getString(1);

				Course_Contents_Pk1.add(CCPk1s);

				Course_Area_Title = rSet.getString(2);

				ColumnName = Course_Area_Title.replaceAll("<(.*?)>", "").replaceAll("â€¢", "").toUpperCase().trim();

				RESULTS += "<th data-column-id='" + ColumnName + "'>" + "<table width='100%'>" + "<tbody>" + "<tr>"
						+ "<td colspan='5'><center><b>" + ColumnName + "</b></center></td>" + "</tr>" + "<tr>"
						+ "<td align='center' width='20%'>F</td>" + "<td align='center' width='20%'>T</td>"
						+ "<td align='center' width='20%'>P</td>" + "<td align='center' width='20%'>FI</td>"
						+ "<td align='center' width='20%'>FF</td>" + "</tr>" + "</tbody>" + "</table>" + "</th>";

				Options += "<option value='" + ColumnName.replaceAll("'", "''") + "'>" + ColumnName + "</option>";
			}

			rSet.close();

			selectQuery.close();

			ReportData.append(RESULTS);
			
			ColumnName = "GEST.";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "HIST.";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			
			RESULTS += "</tr>";
			RESULTS += "</thead>";
			
			ReportData.append("</tr>");
			ReportData.append("</thead>");

			if (StudentIds.size() == 0) {

				RESULTS += "<tbody></tbody></table>";
				
				ReportData.append("<tbody></tbody></table>");
				
				RESULTS += "<div id='ReportData' style='display:none;'>"+ ReportData.toString() +"</div>";
				return RESULTS;
			}

			// -------------------------------------GET ALL FORUMS AND ASSIGNED
			// CORRECT ID-------------------------------------

			ArrayList<String> AllForumNames = new ArrayList<String>();
			ArrayList<String> AllForumIds = new ArrayList<String>();

			ArrayList<String> LinkedForumNames = new ArrayList<String>();
			ArrayList<String> LinkedForumIds = new ArrayList<String>();

			TableData = new StringBuilder();
			TableData.append("SELECT Forum.pk1, Forum.Name FROM COURSE_MAIN COURSE ");
			TableData.append("INNER JOIN CONFERENCE_MAIN CONF ON CONF.CRSMAIN_PK1 = COURSE.PK1 ");
			TableData.append("INNER JOIN FORUM_MAIN FORUM ON FORUM.CONFMAIN_PK1 = CONF.PK1 ");
			TableData.append("where COURSE.PK1 = " + Id);
			TableData.append("ORDER BY FORUM.PK1 ASC ");

			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			rSet = selectQuery.executeQuery();

			while (rSet.next()) {

				AllForumIds.add(rSet.getString(1));
				AllForumNames.add(rSet.getString(2));
			}

			rSet.close();

			selectQuery.close();

			TableData = new StringBuilder();
			TableData.append("SELECT CC.PK1, CC.TITLE FROM COURSE_MAIN CM ");
			TableData.append("INNER JOIN COURSE_CONTENTS CC ON CC.CRSMAIN_PK1 = CM.PK1 ");
			TableData.append("WHERE CM.PK1 = " + Id);
			TableData.append(" AND CC.CNTHNDLR_HANDLE IN ('resource/x-bb-forumlink') ");
			TableData.append("AND ISNULL(CC.PARENT_PK1,-1) != -1 ");
			TableData.append("ORDER BY CC.PK1 ASC ");

			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			rSet = selectQuery.executeQuery();

			while (rSet.next()) {

				LinkedForumIds.add(rSet.getString(1));
				LinkedForumNames.add(rSet.getString(2));
			}

			rSet.close();

			selectQuery.close();

			ArrayList<ArrayList<String>> CorrectIds = null;

			if (AllForumNames.size() > 0 && AllForumIds.size() > 0 && LinkedForumNames.size() > 0
					&& LinkedForumIds.size() > 0) {

				CorrectIds = MatchForums(AllForumNames, AllForumIds, LinkedForumNames, LinkedForumIds);
			}

			// -------------------------------------GET CONTENT FROM SPECIFIC
			// COURSE AREA-------------------------------------

			ArrayList<ArrayList<ArrayList<String>>> Contents = new ArrayList<ArrayList<ArrayList<String>>>();

			ArrayList<ArrayList<String>> TempContent = null;

			ArrayList<String> Assignments = null;
			ArrayList<String> Tests = null;
			ArrayList<String> Forums = null;

			ArrayList<String> ArrayFI = new ArrayList<String>();
			ArrayList<String> ArrayFF = new ArrayList<String>();

			ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<String>>>>> Students = new ArrayList<ArrayList<ArrayList<ArrayList<ArrayList<String>>>>>();

			ArrayList<ArrayList<ArrayList<ArrayList<String>>>> TempResponse = null;

			ArrayList<ArrayList<ArrayList<String>>> RespAssignments = null;
			ArrayList<ArrayList<ArrayList<String>>> RespTests = null;
			ArrayList<ArrayList<ArrayList<String>>> RespForums = null;

			ArrayList<ArrayList<String>> TempRespAssignments = null;
			ArrayList<ArrayList<String>> TempRespTests = null;
			ArrayList<ArrayList<String>> TempRespForums = null;

			ArrayList<String> TempStuResponse = null;
			ArrayList<String> TempStuResponseStatus = null;

			String Cpk1 = "";
			String CHandle = "";
			String StuPk1 = "";
			String RespStatus = "";

			PreparedStatement TempselectQuery = null;
			ResultSet TemprSet = null;

			String ItemTitle = "";
			int ForumIndex = -1;

			for (int i = 0; i < Course_Contents_Pk1.size(); i++) {

				TableData = new StringBuilder();

				TableData.append("SELECT CC.PK1, CC.CNTHNDLR_HANDLE, CC.TITLE FROM COURSE_MAIN CM ");
				TableData.append("INNER JOIN COURSE_CONTENTS CC ON CC.CRSMAIN_PK1 = CM.PK1 ");
				TableData.append("WHERE CM.PK1 = " + Id + " ");
				TableData.append(
						"AND CC.CNTHNDLR_HANDLE IN ('resource/x-bb-forumlink','resource/x-bb-assignment','resource/x-bb-asmt-test-link') ");
				TableData.append("AND ISNULL(CC.PARENT_PK1,-1) != -1 ");
				TableData.append("AND CC.PARENT_PK1 IN (" + Course_Contents_Pk1.get(i) + ", ");
				TableData.append("(SELECT CCS.PK1 FROM COURSE_CONTENTS CCS ");
				TableData.append("WHERE CCS.PARENT_PK1 = " + Course_Contents_Pk1.get(i) + " ");
				TableData.append("AND CCS.CNTHNDLR_HANDLE = 'resource/x-bb-folder') ");
				TableData.append(") ");
				TableData.append("ORDER BY CC.PK1, CC.CNTHNDLR_HANDLE ");

				selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
						ResultSet.CONCUR_READ_ONLY);

				rSet = selectQuery.executeQuery();

				TempContent = new ArrayList<ArrayList<String>>();
				TempResponse = new ArrayList<ArrayList<ArrayList<ArrayList<String>>>>();

				RespAssignments = new ArrayList<ArrayList<ArrayList<String>>>();
				RespTests = new ArrayList<ArrayList<ArrayList<String>>>();
				RespForums = new ArrayList<ArrayList<ArrayList<String>>>();

				Assignments = new ArrayList<String>();
				Tests = new ArrayList<String>();
				Forums = new ArrayList<String>();

				while (rSet.next()) {

					Cpk1 = rSet.getString(1);
					CHandle = rSet.getString(2);
					ItemTitle = rSet.getString(3);

					TempRespAssignments = new ArrayList<ArrayList<String>>();
					TempRespTests = new ArrayList<ArrayList<String>>();
					TempRespForums = new ArrayList<ArrayList<String>>();
					TempStuResponse = new ArrayList<String>();
					TempStuResponseStatus = new ArrayList<String>();

					if (CHandle.equals("resource/x-bb-assignment")) {

						Assignments.add(Cpk1);

						TableData = new StringBuilder();
						TableData.append("SELECT CU.USERS_PK1, ");
						TableData.append("CASE ");
						TableData.append(
								"WHEN NVL(GM.DUE_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'TRUE' ");
						TableData.append("WHEN GG.LAST_ATTEMPT_DATE > GM.DUE_DATE THEN 'FALSE' ");
						TableData.append("WHEN GG.LAST_ATTEMPT_DATE < GM.DUE_DATE THEN 'TRUE' ");
						TableData.append("END \"STATUS\" FROM COURSE_MAIN CM ");
						TableData.append("INNER JOIN GRADEBOOK_MAIN GM ON GM.CRSMAIN_PK1 = CM.PK1 ");
						TableData.append("INNER JOIN GRADEBOOK_GRADE GG ON GG.GRADEBOOK_MAIN_PK1 = GM.PK1 ");
						TableData.append("INNER JOIN COURSE_USERS CU ON CU.PK1 = GG.COURSE_USERS_PK1 ");
						TableData.append("WHERE CM.PK1 = " + Id);
						TableData.append(" AND GM.COURSE_CONTENTS_PK1 = " + Cpk1);

						TempselectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
								ResultSet.CONCUR_READ_ONLY);

						TemprSet = TempselectQuery.executeQuery();

						while (TemprSet.next()) {

							StuPk1 = TemprSet.getString(1);
							RespStatus = TemprSet.getString(2);

							TempStuResponse.add(StuPk1);
							TempStuResponseStatus.add(RespStatus);
						}

						TempselectQuery.close();
						TemprSet.close();

						TempRespAssignments.add(TempStuResponse);
						TempRespAssignments.add(TempStuResponseStatus);

						RespAssignments.add(TempRespAssignments);
					}
					if (CHandle.equals("resource/x-bb-asmt-test-link")) {

						Tests.add(Cpk1);

						if (ItemTitle.toLowerCase().indexOf("formativa inicial") != -1) {

							ArrayFI.add(Cpk1);
						}
						if (ItemTitle.toLowerCase().indexOf("formativa final") != -1) {

							ArrayFF.add(Cpk1);
						}

						TableData = new StringBuilder();
						TableData.append("SELECT CU.USERS_PK1, ");
						TableData.append("CASE ");
						TableData.append(
								"WHEN NVL(GM.DUE_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'TRUE' ");
						TableData.append("WHEN GG.LAST_ATTEMPT_DATE > GM.DUE_DATE THEN 'FALSE' ");
						TableData.append("WHEN GG.LAST_ATTEMPT_DATE < GM.DUE_DATE THEN 'TRUE' ");
						TableData.append("END \"STATUS\" FROM COURSE_MAIN CM ");
						TableData.append("INNER JOIN GRADEBOOK_MAIN GM ON GM.CRSMAIN_PK1 = CM.PK1 ");
						TableData.append("INNER JOIN GRADEBOOK_GRADE GG ON GG.GRADEBOOK_MAIN_PK1 = GM.PK1 ");
						TableData.append("INNER JOIN COURSE_USERS CU ON CU.PK1 = GG.COURSE_USERS_PK1 ");
						TableData.append("WHERE CM.PK1 = " + Id);
						TableData.append(" AND GM.COURSE_CONTENTS_PK1 = " + Cpk1);

						TempselectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
								ResultSet.CONCUR_READ_ONLY);

						TemprSet = TempselectQuery.executeQuery();

						while (TemprSet.next()) {

							StuPk1 = TemprSet.getString(1);
							RespStatus = TemprSet.getString(2);

							TempStuResponse.add(StuPk1);
							TempStuResponseStatus.add(RespStatus);
						}

						TempselectQuery.close();
						TemprSet.close();

						TempRespTests.add(TempStuResponse);
						TempRespTests.add(TempStuResponseStatus);

						RespTests.add(TempRespTests);

					}
					if (CHandle.equals("resource/x-bb-forumlink")) {

						ForumIndex = CorrectIds.get(0).indexOf(Cpk1);

						if (ForumIndex != -1) {

							Cpk1 = CorrectIds.get(1).get(ForumIndex);

							Forums.add(Cpk1);

							TableData = new StringBuilder();
							TableData.append("SELECT MSG.USERS_PK1, ");
							TableData.append("CASE ");
							TableData.append(
									"WHEN NVL(FORUM.END_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'TRUE' ");
							TableData.append("WHEN MSG.LAST_EDIT_DATE > FORUM.END_DATE THEN 'FALSE' ");
							TableData.append("WHEN MSG.LAST_EDIT_DATE < FORUM.END_DATE THEN 'TRUE' ");
							TableData.append("END \"STATUS\" ");
							TableData.append("FROM COURSE_MAIN COURSE ");
							TableData.append("INNER JOIN CONFERENCE_MAIN CONF ON CONF.CRSMAIN_PK1 = COURSE.PK1 ");
							TableData.append("INNER JOIN FORUM_MAIN FORUM ON FORUM.CONFMAIN_PK1 = CONF.PK1 ");
							TableData.append("INNER JOIN MSG_MAIN MSG ON MSG.FORUMMAIN_PK1 = FORUM.PK1 ");
							TableData.append("WHERE MSG.USERS_PK1 IN ( ");
							TableData.append("	SELECT DISTINCT CU.USERS_PK1 FROM COURSE_USERS CU ");
							TableData.append("	INNER JOIN COURSE_MAIN CM ON CM.PK1 = CU.CRSMAIN_PK1 ");
							TableData.append("	INNER JOIN USERS US ON US.PK1 = CU.USERS_PK1 ");
							TableData.append("	WHERE CU.ROLE = 'S' ");
							TableData.append("	AND CM.PK1 = " + Id);
							TableData.append(" ) ");
							TableData.append("AND COURSE.PK1 = " + Id);
							TableData.append(" AND FORUM.PK1 = " + Cpk1);
							TableData.append(" ORDER BY MSG.USERS_PK1");

							TempselectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
									ResultSet.CONCUR_READ_ONLY);

							TemprSet = TempselectQuery.executeQuery();

							while (TemprSet.next()) {

								StuPk1 = TemprSet.getString(1);
								RespStatus = TemprSet.getString(2);

								TempStuResponse.add(StuPk1);
								TempStuResponseStatus.add(RespStatus);
							}

							TempselectQuery.close();
							TemprSet.close();

							TempRespForums.add(TempStuResponse);
							TempRespForums.add(TempStuResponseStatus);

							RespForums.add(TempRespForums);
						}
					}
				}

				TempContent.add(Assignments);
				TempContent.add(Tests);
				TempContent.add(Forums);

				Contents.add(TempContent);

				TempResponse.add(RespAssignments);
				TempResponse.add(RespTests);
				TempResponse.add(RespForums);

				Students.add(TempResponse);
			}

			RESULTS += "<tbody>";
			ReportData.append("<tbody>");
			String AssignmentsImage = "";
			String TestsImage = "";
			String ForumsImage = "";
			String InitialF = "";
			String FinalF = "";
			String AssignmentsImageReportText = "";
			String TestsImageReportText = "";
			String ForumsImageReportText = "";
			String InitialFReportText = "";
			String FinalFReportText = "";
			int StuIndex = -1;

			// CONSOLIDATE TABLE DATA
			for (int i = 0; i < TableInfo.size(); i++) {

				LastName = TableInfo.get(i).get(0);
				FirstName = TableInfo.get(i).get(1);
				User_Id = TableInfo.get(i).get(2);
				LastAccess = TableInfo.get(i).get(3);
				Contact = TableInfo.get(i).get(4);
				History = TableInfo.get(i).get(5);

				RESULTS += "<tr>";
				RESULTS += "<td id='Last" + StudentIds.get(i) + "'>" + LastName + "</td>";
				RESULTS += "<td id='First" + StudentIds.get(i) + "'>" + FirstName + "</td>";
				RESULTS += "<td id='Rut" + StudentIds.get(i) + "'>" + User_Id + "</td>";
				RESULTS += "<td id='LastAccess" + StudentIds.get(i) + "'>" + LastAccess + "</td>";
				
				ReportData.append("<tr>");
				ReportData.append("<td id='Last" + StudentIds.get(i) + "'>" + LastName + "</td>");
				ReportData.append("<td id='First" + StudentIds.get(i) + "'>" + FirstName + "</td>");
				ReportData.append("<td id='Rut" + StudentIds.get(i) + "'>" + User_Id + "</td>");
				ReportData.append("<td id='LastAccess" + StudentIds.get(i) + "'>" + LastAccess + "</td>");
				
				for (int j = 0; j < Course_Contents_Pk1.size(); j++) {

					TempContent = Contents.get(j);
					Assignments = TempContent.get(0);
					Tests = TempContent.get(1);
					Forums = TempContent.get(2);

					AssignmentsImage = "<td align='center' class='tablaHistDatos'><img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/no.png' alt='No Aplica' title='No Aplica'> - <img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/no.png' alt='No Aplica' title='No Aplica'></td>";
					TestsImage = "<td align='center' class='tablaHistDatos'><img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/no.png' alt='No Aplica' title='No Aplica'> - <img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/no.png' alt='No Aplica' title='No Aplica'></td>";
					ForumsImage = "<td align='center' class='tablaHistDatos'><img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/no.png' alt='No Aplica' title='No Aplica'> - <img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/no.png' alt='No Aplica' title='No Aplica'></td>";
					InitialF = "<td align='center' class='tablaHistDatos'><img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/no.png' alt='No Aplica' title='No Aplica'> - <img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/no.png' alt='No Aplica' title='No Aplica'></td>";
					FinalF = "<td align='center' class='tablaHistDatos'><img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/no.png' alt='No Aplica' title='No Aplica'> - <img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/no.png' alt='No Aplica' title='No Aplica'></td>";
					
					AssignmentsImageReportText = "<td align='center' class='tablaHistDatos'>No Aplica - No Aplica</td>";
					TestsImageReportText = "<td align='center' class='tablaHistDatos'>No Aplica - No Aplica</td>";
					ForumsImageReportText = "<td align='center' class='tablaHistDatos'>No Aplica - No Aplica</td>";
					InitialFReportText = "<td align='center' class='tablaHistDatos'>No Aplica - No Aplica</td>";
					FinalFReportText = "<td align='center' class='tablaHistDatos'>No Aplica - No Aplica</td>";
					
					if (Assignments.size() != 0) {

						AssignmentsImage = "<td align='center' class='tablaHistDatos'><img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' alt='No Aplica' title='No Aplica'> - <img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' alt='No Aplica' title='No Aplica'></td>";
						AssignmentsImageReportText = "<td align='center' class='tablaHistDatos'>No - No</td>";
						
						for (int k = 0; k < Students.get(j).get(0).size(); k++) {

							for (int l = 0; l < Students.get(j).get(0).get(k).size(); l++) {

								StuIndex = Students.get(j).get(0).get(k).get(0).indexOf(StudentIds.get(i));

								if (StuIndex != -1) {

									AssignmentsImage = "<td align='center' class='tablaHistDatos'><img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/chk.png' alt='No Aplica' title='No Aplica'> - ";
									AssignmentsImageReportText = "<td align='center' class='tablaHistDatos'> Si - ";
									
									if (Students.get(j).get(0).get(k).get(1).get(StuIndex).equals("TRUE")) {

										AssignmentsImage += "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/chk.png' alt='No Aplica' title='No Aplica'></td>";
										AssignmentsImageReportText += "Si</td>";
									} else {

										AssignmentsImage += "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' alt='No Aplica' title='No Aplica'></td>";
										AssignmentsImageReportText += "No</td>";
									}
								}
							}
						}
					}
					if (Tests.size() != 0) {

						for (int l = 0; l < Tests.size(); l++) {

							if (ArrayFI.indexOf(Tests.get(l)) != -1) {

								InitialF = "<td align='center' class='tablaHistDatos'><img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' alt='No Aplica' title='No Aplica'> - <img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' alt='No Aplica' title='No Aplica'></td>";
								InitialFReportText = "<td align='center' class='tablaHistDatos'>No - No</td>";
							} else if (ArrayFF.indexOf(Tests.get(l)) != -1) {

								FinalF = "<td align='center' class='tablaHistDatos'><img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' alt='No Aplica' title='No Aplica'> - <img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' alt='No Aplica' title='No Aplica'></td>";
								FinalFReportText = "<td align='center' class='tablaHistDatos'>No - No</td>";
							} else {
								
								TestsImage = "<td align='center' class='tablaHistDatos'><img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' alt='No Aplica' title='No Aplica'> - <img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' alt='No Aplica' title='No Aplica'></td>";
								TestsImageReportText = "<td align='center' class='tablaHistDatos'>No - No</td>";
							}
						}

						for (int k = 0; k < Students.get(j).get(1).size(); k++) {

							for (int l = 0; l < Students.get(j).get(1).get(k).size(); l++) {

								StuIndex = Students.get(j).get(1).get(k).get(0).indexOf(StudentIds.get(i));

								if (StuIndex != -1) {

									if (ArrayFI.indexOf(Tests.get(k)) != -1) {

										InitialF = "<td align='center' class='tablaHistDatos'><img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/chk.png' alt='No Aplica' title='No Aplica'> - ";
										InitialFReportText = "<td align='center' class='tablaHistDatos'>Si - </td>";
										
										if (Students.get(j).get(1).get(k).get(1).get(StuIndex).equals("TRUE")) {

											InitialF += "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/chk.png' alt='No Aplica' title='No Aplica'></td>";
											InitialFReportText += "Si</td>";
										} else {

											InitialF += "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' alt='No Aplica' title='No Aplica'></td>";
											InitialFReportText += "No</td>";
										}
									} else if (ArrayFF.indexOf(Tests.get(k)) != -1) {

										FinalF = "<td align='center' class='tablaHistDatos'><img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/chk.png' alt='No Aplica' title='No Aplica'> - ";
										FinalFReportText = "<td align='center' class='tablaHistDatos'>Si - </td>";
										
										if (Students.get(j).get(1).get(k).get(1).get(StuIndex).equals("TRUE")) {

											FinalF += "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/chk.png' alt='No Aplica' title='No Aplica'></td>";
											FinalFReportText += "Si</td>";
										} else {

											FinalF += "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' alt='No Aplica' title='No Aplica'></td>";
											FinalFReportText += "No</td>";
										}
									} else {
										
										TestsImage = "<td align='center' class='tablaHistDatos'><img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/chk.png' alt='No Aplica' title='No Aplica'> - ";
										TestsImageReportText = "<td align='center' class='tablaHistDatos'>Si - </td>";
										
										if (Students.get(j).get(1).get(k).get(1).get(StuIndex).equals("TRUE")) {

											TestsImage += "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/chk.png' alt='No Aplica' title='No Aplica'></td>";
											TestsImageReportText += "Si</td>";
										} else {

											TestsImage += "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' alt='No Aplica' title='No Aplica'></td>";
											TestsImageReportText += "No</td>";
										}
									}
								}
							}
						}
					}
					if (Forums.size() != 0) {

						ForumsImage = "<td align='center' class='tablaHistDatos'><img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' alt='No Aplica' title='No Aplica'> - <img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' alt='No Aplica' title='No Aplica'></td>";
						ForumsImageReportText = "<td align='center' class='tablaHistDatos'>No - No</td>";
						
						for (int k = 0; k < Students.get(j).get(2).size(); k++) {

							for (int l = 0; l < Students.get(j).get(2).get(k).size(); l++) {

								StuIndex = Students.get(j).get(2).get(k).get(0).indexOf(StudentIds.get(i));

								if (StuIndex != -1) {

									ForumsImage = "<td align='center' class='tablaHistDatos'><img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/chk.png' alt='No Aplica' title='No Aplica'> - ";
									ForumsImageReportText = "<td align='center' class='tablaHistDatos'>Si - </td>";
									
									if (Students.get(j).get(2).get(k).get(1).get(StuIndex).equals("TRUE")) {

										ForumsImage += "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/chk.png' alt='No Aplica' title='No Aplica'></td>";
										ForumsImageReportText += "Si</td>";
									} else {

										ForumsImage += "<img src='/webapps/lnoh-AIEPMTOOL-BBLEARN/Images/sin.png' alt='No Aplica' title='No Aplica'></td>";
										ForumsImageReportText += "No</td>";
									}
								}
							}
						}
					}

					RESULTS += "<td>" + "<table width='100%' cellspacing='1' cellpadding='1'>" + "<tbody>"
							+ "<tr class='celdaColumna'>" + ForumsImage + AssignmentsImage + TestsImage + InitialF
							+ FinalF + "</tr>" + "</tbody>" + "</table>" + "</td>";
					
					ReportData.append("<td>" + "<table width='100%' cellspacing='1' cellpadding='1'>" + "<tbody>");
					ReportData.append("<tr class='celdaColumna'>" + ForumsImageReportText + AssignmentsImageReportText + TestsImageReportText + InitialFReportText);
					ReportData.append(FinalFReportText + "</tr>" + "</tbody>" + "</table>" + "</td>");
				}

				RESULTS += "<td>" + Contact + "</td>";
				RESULTS += "<td>" + History + "</td>";
				RESULTS += "</tr>";
				ReportData.append("</tr>");
			}

			RESULTS += "</tbody>";
			RESULTS += "</table>";
			
			ReportData.append("</tbody>");
			ReportData.append("</table>");
			//RESULTS += "<label>Total: " + TableInfo.size() + "</label>";

			RESULTS += "<div id='UnitsOptions' style='display:none;'>" + Options + "</div>";
			
			RESULTS += "<div id='ReportData' style='display:none;'>"+ ReportData.toString() +"</div>";

			// RESULTS += "<br> Contents: " + Contents.toString();
			// RESULTS += "<br> Students: " + Students.toString();

		} catch (java.sql.SQLException sE) {

			RESULTS += "java.sql.SQLException sE: " + sE.toString();

		} catch (ConnectionNotAvailableException cE) {

			RESULTS += "ConnectionNotAvailableException cE :" + cE.toString();
		} finally {

			if (conn != null) {
				cManager.releaseConnection(conn);
			}
		}

		return RESULTS;
	}

	public String parseTime(String mins) {

		long milliseconds = (long) (Double.parseDouble(mins) * 60000);

		String FORMAT = "%02d:%02d:%02d";
		String FinalString = "";
		String TempString = "";
		String[] TempArray = null;
		int steps = 1;

		TempString = String.format(FORMAT, TimeUnit.MILLISECONDS.toHours(milliseconds),
				TimeUnit.MILLISECONDS.toMinutes(milliseconds)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds)),
				TimeUnit.MILLISECONDS.toSeconds(milliseconds)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));

		TempArray = TempString.split(":");

		if (!TempArray[0].equals("00")) {

			TempString = " horas ";

			if (Integer.parseInt(TempArray[0]) == 1) {

				TempString = " hora ";
			}

			FinalString += Integer.parseInt(TempArray[0]) + TempString;
			steps++;
		}
		if (!TempArray[1].equals("00")) {

			TempString = " mins ";

			if (Integer.parseInt(TempArray[1]) == 1) {

				TempString = " min ";
			}

			FinalString += Integer.parseInt(TempArray[1]) + TempString;

			if (steps == 2) {

				return FinalString;
			}
		}

		TempString = " segs ";

		if (Integer.parseInt(TempArray[2]) == 1) {

			TempString = " seg ";
		}

		FinalString += Integer.parseInt(TempArray[2]) + TempString;

		return FinalString;
	}

	public static void main(String[] args) {

	}

	public ArrayList<ArrayList<String>> MatchForums(ArrayList<String> a, ArrayList<String> a1, ArrayList<String> b,
			ArrayList<String> b1) {
		
		ArrayList<String> OldIds = new ArrayList<String>();
		ArrayList<String> NewIds = new ArrayList<String>();

		ArrayList<String> TempA = null;
		ArrayList<String> TempB = null;
		ArrayList<String> TempArray = null;
		ArrayList<String> NotFound = new ArrayList<String>();

		int c = 0;
		int e = 0;
		int d = 0;
		int minVal = -1;
		int maxVal = -1;
		int minDistance = -1;

		ArrayList<Integer> taken = new ArrayList<Integer>();
		ArrayList<Integer> toRemove = null;
		// StringUtils.getLevenshteinDistance
		int index = -1;

		for (int i = 0; i < b.size(); i++) {

			minVal = Integer.MAX_VALUE;
			maxVal = -1;
			index = -1;
			minDistance = Integer.MAX_VALUE;

			TempB = new ArrayList<String>(
					Arrays.asList(b.get(i).toString().replaceAll("[^A-Za-z0-9 ]", "").toLowerCase().trim().split(" ")));

			for (int j = 0; j < a.size(); j++) {

				TempA = new ArrayList<String>(Arrays
						.asList(a.get(j).toString().replaceAll("[^A-Za-z0-9 ]", "").toLowerCase().trim().split(" ")));
				c = 0;
				e = 0;
				d = 0;

				if (b.get(i).toString().equals(a.get(j).toString()) && taken.indexOf(j) == -1) {

					index = j;
					break;
				}

				toRemove = new ArrayList<Integer>();

				TempArray = new ArrayList<String>();

				for (int k = 0; k < TempB.size(); k++) {

					if (TempA.indexOf(TempB.get(k)) != -1) {

						c++;
						TempArray.add(TempB.get(k));
					} else {

						e++;
						toRemove.add(k);
					}
				}

				d = StringUtils.getLevenshteinDistance(TempArray.toString(), TempA.toString());

				if (c >= maxVal && d < minDistance && e <= minVal && taken.indexOf(j) == -1
						&& ((((double) c) / ((double) TempB.size())) * 100) >= 70) {

					minDistance = d;
					minVal = e;
					maxVal = c;
					index = j;

					if (TempArray.toString().equals(TempA.toString())) {

						// System.out.println("Entro 2");
						minDistance = d;
						minVal = e;
						maxVal = c;
						index = j;
					} else {
						if (TempArray.size() > 0) {
							if (TempA.toString().replace("[", "").replace("]", "").replaceAll(",", "").indexOf(
									TempArray.toString().replace("[", "").replace("]", "").replaceAll(",", "")) != -1) {
								// System.out.println("Entro 3");
								minDistance = d;
								minVal = e;
								maxVal = c;
								index = j;
							}
						}
					}

				}
			}

			if (index != -1) {

				taken.add(index);
				OldIds.add(b1.get(i));
				NewIds.add(a1.get(index));
			} else {

				NotFound.add(b.get(i));
			}
		}

		ArrayList<ArrayList<String>> CorrectIds = new ArrayList<ArrayList<String>>();

		CorrectIds.add(OldIds);
		CorrectIds.add(NewIds);

		return CorrectIds;
	}

	public String getInstructorActivity(String CourseId, int page, String Sede) {

		ConnectionManager cManager = null;
		Connection conn = null;
		PreparedStatement selectQuery = null;

		StringBuilder Data = new StringBuilder();
		Data.append("<table>");
		Data.append("<tr>");
		Data.append("<td>");
		Data.append("DOCENTE");
		Data.append("</td>");
		Data.append("<td>MÓDULO</td>");
		Data.append("<td>Sede</td>");
		Data.append("<td>");
		Data.append("ÚLTIMO INGRESO");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("TIEMPO DEDICADO");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("NUM.POST");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("ÚLTIMO POST");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("VENCIMIENTO");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("PARTICIPANTES");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("ENTREGAS");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("FALTA CALIF.");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("% SIN CALIF.");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("VENCIMIENTO");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("PARTICIPANTES");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("ENTREGAS");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("FALTA CALIF.");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("% SIN CALIF.");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("VENCIMIENTO");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("PARTICIPANTES");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("ENTREGAS");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("FALTA CALIF.");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("% SIN CALIF.");
		Data.append("</td>");
		Data.append("</tr>");

		String RESULTS = "";

		try {

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			// GET ALL
			// COURSES/INSTRUCTORS/DAYS_SINCE_LAST_ACCESS/TOTAL_TIME_SPENT_IN_COURSE/TOTAL_POSTS/DAYS_SINCE_LAST_POST
			
			int firstRow = (page - 1) * pageSize + 1;
			int lastRow = (page * pageSize) + 1;
			
			StringBuilder TableData = new StringBuilder();
			TableData.append("SELECT * FROM ( SELECT a.*, rownum rowcount FROM (");
			TableData.append("SELECT INFO2.*,");
			TableData.append("CASE");
			TableData.append("	WHEN INFO2.NUM_POSTS = 0 THEN 'NUNCA'");
			TableData.append("	ELSE");
			TableData.append("	(");
			TableData.append("		CONCAT");
			TableData.append("		(	");
			TableData.append("			(TRUNC(SYSDATE) - TRUNC((");
			TableData.append(
					"									SELECT MAX(MSG.LAST_EDIT_DATE) FROM COURSE_MAIN COURSE");
			TableData.append(
					"									INNER JOIN CONFERENCE_MAIN CONF ON COURSE.PK1 = CONF.CRSMAIN_PK1");
			TableData.append(
					"									INNER JOIN FORUM_MAIN FORUM ON FORUM.CONFMAIN_PK1 = CONF.PK1");
			TableData.append(
					"									INNER JOIN MSG_MAIN MSG ON MSG.FORUMMAIN_PK1 = FORUM.PK1");
			TableData.append("									INNER JOIN USERS USERS ON USERS.PK1 = MSG.USERS_PK1");
			TableData.append("									WHERE COURSE.PK1 = INFO2.CM_PK1");
			TableData.append("									AND MSG.USERS_PK1 = INFO2.US_PK1");
			TableData.append("								))");
			TableData.append("			),' días'");
			TableData.append("		)");
			TableData.append("	)");
			TableData.append("END \"LAST_POST\",");
			TableData.append(
					" (SELECT COUNT(*) FROM COURSE_USERS CUU WHERE CUU.ROLE = 'S' AND CUU.CRSMAIN_PK1 = INFO2.CM_PK1) \"NUM_STUDENTS\"");
			TableData.append(" FROM(");
			TableData.append("	SELECT INFO.*,");
			TableData.append("	CASE");
			TableData.append("		WHEN INFO.LAST_ACCESS = 'NUNCA' THEN 0");
			TableData.append(
					"		ELSE (SELECT SUM(COURSE_ACCESS_MINUTES) FROM ODS_AA_COURSE_ACTIVITY WHERE USER_PK1 = INFO.US_PK1 AND COURSE_PK1 = INFO.CM_PK1)");
			TableData.append("	END \"TIME\",");
			TableData.append("	CASE");
			TableData.append("		WHEN INFO.LAST_ACCESS = 'NUNCA' THEN 0");
			TableData.append("		ELSE( ");
			TableData.append("			SELECT COUNT(*) FROM COURSE_MAIN COURSE");
			TableData.append("			INNER JOIN CONFERENCE_MAIN CONF ON COURSE.PK1 = CONF.CRSMAIN_PK1");
			TableData.append("			INNER JOIN FORUM_MAIN FORUM ON FORUM.CONFMAIN_PK1 = CONF.PK1");
			TableData.append("			INNER JOIN MSG_MAIN MSG ON MSG.FORUMMAIN_PK1 = FORUM.PK1");
			TableData.append("			INNER JOIN USERS USERS ON USERS.PK1 = MSG.USERS_PK1");
			TableData.append("			WHERE COURSE.PK1 = INFO.CM_PK1");
			TableData.append("			AND MSG.USERS_PK1 = INFO.US_PK1");
			TableData.append("		)");
			TableData.append("	END \"NUM_POSTS\"");
			TableData.append("	FROM(");
			TableData.append(
					"		SELECT CM.PK1 \"CM_PK1\",US.PK1 \"US_PK1\", US.FIRSTNAME, US.LASTNAME,CM.START_DATE, CM.COURSE_ID, CS.SEDE, ");
			TableData.append("		CASE");
			TableData.append(
					"		WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'NUNCA'");
			TableData.append("		ELSE");
			TableData.append("		CONCAT((TRUNC(SYSDATE)-TRUNC(CU.LAST_ACCESS_DATE)),' días')");
			TableData.append("		END \"LAST_ACCESS\"");
			TableData.append("		FROM COURSE_MAIN CM");
			TableData.append("		INNER JOIN COURSE_USERS CU ON CU.CRSMAIN_PK1 = CM.PK1");
			TableData.append("		INNER JOIN USERS US ON US.PK1 = CU.USERS_PK1");
			TableData.append("		INNER JOIN INSTITUTION_ROLES IR ON US.INSTITUTION_ROLES_PK1 = IR.PK1");
			if(Sede.equals("Todo")) {
				TableData.append(" INNER JOIN (");
				TableData.append(" select sede.name as sede, course_id");
				TableData.append(" from domain_course_coll_uid dom_crs inner join course_main course on course.pk1 = dom_crs.course_main_pk1 ");
				TableData.append(" INNER JOIN domain on domain.pk1 = dom_crs.domain_pk1 ");
				TableData.append(" inner join mi_node modulo  on modulo.domain_pk1 = domain.pk1 ");
				TableData.append(" inner join mi_node semestre on semestre.pk1 = modulo.parent_pk1 ");
				TableData.append(" inner join mi_node anio on anio.pk1 = semestre.parent_pk1 ");
				TableData.append(" inner join mi_node carrera on carrera.pk1 = anio.parent_pk1 ");
				TableData.append(" inner join mi_node escuela on escuela.pk1 = carrera.parent_pk1 ");
				TableData.append(" inner join mi_node sede on sede.pk1 = escuela.parent_pk1 ");
				TableData.append(" ) CS ON CS.COURSE_ID = CM.COURSE_ID ");
			} else {
				TableData.append(" INNER JOIN (");
				TableData.append(" select sede.name as sede, course_id");
				TableData.append(" from domain_course_coll_uid dom_crs inner join course_main course on course.pk1 = dom_crs.course_main_pk1 ");
				TableData.append(" INNER JOIN domain on domain.pk1 = dom_crs.domain_pk1 ");
				TableData.append(" inner join mi_node modulo  on modulo.domain_pk1 = domain.pk1 ");
				TableData.append(" inner join mi_node semestre on semestre.pk1 = modulo.parent_pk1 ");
				TableData.append(" inner join mi_node anio on anio.pk1 = semestre.parent_pk1 ");
				TableData.append(" inner join mi_node carrera on carrera.pk1 = anio.parent_pk1 ");
				TableData.append(" inner join mi_node escuela on escuela.pk1 = carrera.parent_pk1 ");
				TableData.append(" inner join mi_node sede on sede.pk1 = escuela.parent_pk1 ");
				TableData.append(" WHERE sede.name = '"+Sede+"') CS ON CS.COURSE_ID = CM.COURSE_ID ");
			}
			
			TableData.append("		WHERE CU.ROLE = 'Docente'");
			TableData.append("		AND CM.ROW_STATUS = 0 AND CU.ROW_STATUS = 0");
			if (CourseId.contains("%")) {

				TableData.append(" AND CM.COURSE_ID LIKE '"+CourseId+"' ");
			} else {
				TableData.append(" AND CM.COURSE_ID = '"+CourseId+"' ");
			}
			TableData.append("		AND CM.COURSE_ID NOT LIKE 'BASE%'");
			TableData.append("		ORDER BY CM.PK1 DESC");
			TableData.append("	) \"INFO\"");
			TableData.append(") \"INFO2\"");
			TableData.append(") a WHERE rownum < " + lastRow + " ) WHERE rowcount >= " + firstRow);
			
			System.out.println(TableData.toString());
			
			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rSet = selectQuery.executeQuery();

			RESULTS = "<table id='grid-basic' class='table table-condensed table-hover table-striped'>";
			RESULTS += "<thead>";
			RESULTS += "<tr>";

			String ColumnName = "";

			ColumnName = "DOCENTE";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "MÓDULO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";
			
			ColumnName = "FECHA DE INICIO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";
			
			ColumnName = "SEDE";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "ÚLT. ING.";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "TMPO. DED.";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "NUM. POST";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "ÚLT. POST";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "UNIDAD 1";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + "<table width='100%'>" + "<tbody>" + "<tr>"
					+ "<td colspan='5'><center><b>" + ColumnName + "</b></center></td>" + "</tr>" + "<tr>"
					+ "<td align='center' width='20%'>VEN</td>" + "<td align='center' width='20%'>PT</td>"
					+ "<td align='center' width='20%'>EN</td>" + "<td align='center' width='20%'>FC</td>"
					+ "<td align='center' width='20%'>%</td>" + "</tr>" + "</tbody>" + "</table>" + "</th>";

			ColumnName = "UNIDAD 2";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + "<table width='100%'>" + "<tbody>" + "<tr>"
					+ "<td colspan='5'><center><b>" + ColumnName + "</b></center></td>" + "</tr>" + "<tr>"
					+ "<td align='center' width='20%'>VEN</td>" + "<td align='center' width='20%'>PT</td>"
					+ "<td align='center' width='20%'>EN</td>" + "<td align='center' width='20%'>FC</td>"
					+ "<td align='center' width='20%'>%</td>" + "</tr>" + "</tbody>" + "</table>" + "</th>";

			ColumnName = "UNIDAD 3";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + "<table width='100%'>" + "<tbody>" + "<tr>"
					+ "<td colspan='5'><center><b>" + ColumnName + "</b></center></td>" + "</tr>" + "<tr>"
					+ "<td align='center' width='20%'>VEN</td>" + "<td align='center' width='20%'>PT</td>"
					+ "<td align='center' width='20%'>EN</td>" + "<td align='center' width='20%'>FC</td>"
					+ "<td align='center' width='20%'>%</td>" + "</tr>" + "</tbody>" + "</table>" + "</th>";

			RESULTS += "</tr>";
			RESULTS += "</thead>";
			RESULTS += "<tbody>";

			ArrayList<String> CoursePk1s = new ArrayList<String>();
			ArrayList<String> InstructorsPk1s = new ArrayList<String>();

			String CoursePk1 = "";
			String InstructorsPk1 = "";
			String Name = "";
			String CourseName = "";
			String Last_Access = "";
			String START_DATE = "";
			String TimeSpent = "";
			String NumPosts = "";
			String Last_Post = "";
			String Sede_Curso = "";
			String End_Date = "";
			String Num_Stu = "";
			String Num_Assign = "";
			String Not_Graded = "";
			String Percent = "";

			String Last_AccessColor = "style=\"background-color: indianred;\"";
			String PercentColor = "";

			long count = 0L;

			int NumAreas = -1;
			String AssignId = "NOT FOUND";

			ResultSet TemprSet = null;
			PreparedStatement TempselectQuery = null;

			ArrayList<String> Course_Contents_Pk1 = null;

			while (rSet.next()) {

				CoursePk1 = rSet.getString("CM_PK1");
				InstructorsPk1 = rSet.getString("US_PK1");
				Name = rSet.getString("FIRSTNAME") + " " + rSet.getString("LASTNAME");
				CourseName = rSet.getString("COURSE_ID");
				Last_Access = rSet.getString("LAST_ACCESS");
				TimeSpent = rSet.getString("TIME");
				NumPosts = rSet.getString("NUM_POSTS");
				Last_Post = rSet.getString("LAST_POST");
				Num_Stu = rSet.getString("NUM_STUDENTS");
				Sede_Curso = rSet.getString("SEDE");
				START_DATE = rSet.getString("START_DATE");
				
				if(TimeSpent == null){
					
					TimeSpent = "0";
				}
				
				if(Last_Access == null){
					
					Last_Access = "NUNCA";
				}

				if (!TimeSpent.equals("0")) {

					TimeSpent = parseTime(TimeSpent);
				} else {

					TimeSpent += " segs";
				}

				if (Last_Access.equals("NUNCA")) {

					Last_AccessColor = "style=\"background-color: indianred;\"";
				} else {
					Last_AccessColor = "";

					if (Integer.parseInt(Last_Access.split(" ")[0]) > 7) {

						Last_AccessColor = "style=\"background-color: indianred;\"";
					}
				}

				RESULTS += "<tr>";
				RESULTS += "<td>" + Name + "</td>";
				RESULTS += "<td><a target='_blank' href='/webapps/blackboard/execute/courseMain?course_id="
						+ rSet.getString(1) + "'>" + CourseName + "</a></td>";
				RESULTS += "<td>"+START_DATE.split(" ")[0]+"</td>";
				RESULTS += "<td>"+Sede_Curso+"</td>";
				RESULTS += "<td " + Last_AccessColor + ">" + Last_Access + "</td>";
				RESULTS += "<td>" + TimeSpent + "</td>";
				RESULTS += "<td>" + NumPosts + "</td>";
				RESULTS += "<td>" + Last_Post + "</td>";

				Data.append("<tr>");
				Data.append("<td>" + Name + "</td>");
				Data.append("<td>" + CourseName + "</td>");
				Data.append("<td>" + Last_Access + "</td>");
				Data.append("<td>" + TimeSpent + "</td>");
				Data.append("<td>" + NumPosts + "</td>");
				Data.append("<td>" + Last_Post + "</td>");

				// GET COURSE AREAS
				TableData = new StringBuilder();
				TableData.append("SELECT * FROM( ");
				TableData.append(
						"	SELECT CT.COURSE_CONTENTS_PK1, CT.LABEL, CT.POSITION, COUNT(*) FROM COURSE_MAIN CM ");
				TableData.append("	INNER JOIN COURSE_TOC CT ON CT.CRSMAIN_PK1 = CM.PK1 ");
				TableData.append("	INNER JOIN COURSE_CONTENTS CC ON CC.PARENT_PK1 = CT.COURSE_CONTENTS_PK1 ");
				TableData.append("	WHERE CM.PK1 = " + CoursePk1 + " ");
				TableData.append("	AND CT.TARGET_TYPE = 'CONTENT_LINK' ");
				TableData.append(
						"	AND CC.CNTHNDLR_HANDLE IN ('resource/x-bb-forumlink','resource/x-bb-assignment','resource/x-bb-asmt-test-link') ");
				TableData.append("	GROUP BY CT.COURSE_CONTENTS_PK1, CT.LABEL, CT.POSITION ");
				TableData.append(") CTT ORDER BY CTT.POSITION ASC ");

				TempselectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
						ResultSet.CONCUR_READ_ONLY);

				TemprSet = TempselectQuery.executeQuery();

				Course_Contents_Pk1 = new ArrayList<String>();

				while (TemprSet.next()) {

					Course_Contents_Pk1.add(TemprSet.getString(1));
				}

				TemprSet.close();

				TempselectQuery.close();

				NumAreas = Course_Contents_Pk1.size();

				if (NumAreas > 3) {

					NumAreas = 3;
				}

				for (int i = 0; i < NumAreas; i++) {

					AssignId = "NOT FOUND";

					TableData = new StringBuilder();
					TableData.append("SELECT CC.PK1 FROM COURSE_MAIN CM ");
					TableData.append("INNER JOIN COURSE_CONTENTS CC ON CC.CRSMAIN_PK1 = CM.PK1 ");
					TableData.append("WHERE CM.PK1 = " + CoursePk1 + " ");
					TableData.append("AND CC.CNTHNDLR_HANDLE = 'resource/x-bb-assignment' ");
					TableData.append("AND ISNULL(CC.PARENT_PK1,-1) != -1 ");
					TableData.append("AND CC.PARENT_PK1 IN (" + Course_Contents_Pk1.get(i) + ", ");
					TableData.append("(SELECT CCS.PK1 FROM COURSE_CONTENTS CCS ");
					TableData.append("WHERE CCS.PARENT_PK1 = " + Course_Contents_Pk1.get(i) + " ");
					TableData.append("AND CCS.CNTHNDLR_HANDLE = 'resource/x-bb-folder') ");
					TableData.append(") ");
					TableData.append("AND ROWNUM = 1 ");
					TableData.append("ORDER BY CC.PK1 ");

					TempselectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
							ResultSet.CONCUR_READ_ONLY);

					TemprSet = TempselectQuery.executeQuery();

					while (TemprSet.next()) {

						AssignId = TemprSet.getString(1);

						if (AssignId == null) {

							AssignId = "NOT FOUND";
						}
					}

					TemprSet.close();

					TempselectQuery.close();

					if (!AssignId.equals("NOT FOUND")) {

						TableData = new StringBuilder();
						TableData.append("SELECT ( ");
						TableData.append("	SELECT ");
						TableData.append("		CASE");
						TableData.append(
								"			WHEN NVL(GM.DUE_DATE,TO_DATE('0001/01/01', 'YYYY-MM-DD')) = TO_DATE('0001/01/01', 'YYYY-MM-DD') THEN 'N/A'");
						TableData.append("			ELSE CONCAT(TRUNC(GM.DUE_DATE),' ')");
						TableData.append("		END \"END_DATE\"");
						TableData.append("		FROM COURSE_MAIN CM");
						TableData.append("		INNER JOIN GRADEBOOK_MAIN GM ON GM.CRSMAIN_PK1 = CM.PK1");
						TableData.append("		WHERE CM.PK1 = " + CoursePk1 + " ");
						TableData.append("		AND GM.COURSE_CONTENTS_PK1 = " + AssignId + "  ");
						TableData.append(") \"END_DATE\",INFO2.*,");
						TableData.append("	CASE");
						TableData.append("		WHEN INFO2.SUBMISSION = 0 THEN '0%'");
						TableData.append("		ELSE (CONCAT(TRUNC((INFO2.NOT_GRADED/INFO2.SUBMISSION)*100),'%'))");
						TableData.append("	END \"PERCENTAGE\"");
						TableData.append(" FROM(");
						TableData.append("	SELECT INFO.*,");
						TableData.append("		CASE");
						TableData.append("			WHEN INFO.SUBMISSION = 0 THEN 0");
						TableData.append("			ELSE (");
						TableData.append(
								"				SELECT COUNT(DISTINCT GG.COURSE_USERS_PK1) FROM GRADEBOOK_MAIN GM");
						TableData.append("				INNER JOIN COURSE_MAIN CM ON CM.PK1 = GM.CRSMAIN_PK1");
						TableData.append(
								"				INNER JOIN GRADEBOOK_GRADE GG ON GG.GRADEBOOK_MAIN_PK1 = GM.PK1");
						TableData.append("				INNER JOIN ATTEMPT AT ON AT.GRADEBOOK_GRADE_PK1 = GG.PK1");
						TableData.append("				WHERE CM.PK1 = 	" + CoursePk1 + " ");
						TableData.append("				AND NVL(AT.FIRST_GRADED_DATE,'01/JAN/0001') = '01/JAN/0001'");
						TableData.append("				AND AT.STATUS != 3");
						TableData.append("				AND GM.COURSE_CONTENTS_PK1 = " + AssignId + " ");
						TableData.append("			)");
						TableData.append("		END \"NOT_GRADED\"");
						TableData.append("	 FROM(");
						TableData.append(
								"		SELECT COUNT(DISTINCT GG.COURSE_USERS_PK1) \"SUBMISSION\" FROM GRADEBOOK_MAIN GM");
						TableData.append("		INNER JOIN COURSE_MAIN CM ON CM.PK1 = GM.CRSMAIN_PK1");
						TableData.append("		INNER JOIN GRADEBOOK_GRADE GG ON GG.GRADEBOOK_MAIN_PK1 = GM.PK1");
						TableData.append("		INNER JOIN ATTEMPT AT ON AT.GRADEBOOK_GRADE_PK1 = GG.PK1");
						TableData.append("		WHERE CM.PK1 = 	" + CoursePk1 + " ");
						TableData.append("		AND GM.COURSE_CONTENTS_PK1 = " + AssignId + " ");
						TableData.append("	) \"INFO\" ");
						TableData.append(") \"INFO2\"");

						TempselectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
								ResultSet.CONCUR_READ_ONLY);

						TemprSet = TempselectQuery.executeQuery();

						while (TemprSet.next()) {

							End_Date = TemprSet.getString(1);
							Num_Assign = TemprSet.getString(2);
							Not_Graded = TemprSet.getString(3);
							Percent = TemprSet.getString(4);

							if (End_Date == null) {

								End_Date = "N/A";
							} else {
								if (!End_Date.equals("N/A")) {

									End_Date = End_Date.split(" ")[0];
								}
							}
						}

						TemprSet.close();

						TempselectQuery.close();

						PercentColor = "";

						if (Integer.parseInt(Percent.replace("%", "")) > 69) {

							PercentColor = ";background-color: indianred;";
						}

						RESULTS += "<td>" + "<table width='100%' cellspacing='1' cellpadding='1'>" + "<tbody>"
								+ "<tr class='celdaColumna'>"
								+ "<td align='center' class='tablaHistDatos' style='width:21%'>" + End_Date + "</td>"
								+ "<td align='center' class='tablaHistDatos' style='width:20%'>" + Num_Stu + "</td>"
								+ "<td align='center' class='tablaHistDatos' style='width:20%'>" + Num_Assign + "</td>"
								+ "<td align='center' class='tablaHistDatos' style='width:20%'>" + Not_Graded + "</td>"
								+ "<td align='center' class='tablaHistDatos' style='width:20%" + PercentColor + "'>"
								+ Percent + "</td>" + "</tr>" + "</tbody>" + "</table>" + "</td>";

						Data.append("<td>" + End_Date + "</td>");
						Data.append("<td>" + Num_Stu + "</td>");
						Data.append("<td>" + Num_Assign + "</td>");
						Data.append("<td>" + Not_Graded + "</td>");
						Data.append("<td>" + Percent + "</td>");
					} else {

						RESULTS += "<td>" + "<table width='100%' cellspacing='1' cellpadding='1'>" + "<tbody>"
								+ "<tr class='celdaColumna'>"
								+ "<td align='center' class='tablaHistDatos' style='width:100%'>Sin Tarea Sumativa</td>"
								+ "</tr>" + "</tbody>" + "</table>" + "</td>";

						Data.append("<td>-</td>");
						Data.append("<td>-</td>");
						Data.append("<td>-</td>");
						Data.append("<td>-</td>");
						Data.append("<td>-</td>");
					}
				}

				for (int i = 0; i < (3 - NumAreas); i++) {

					RESULTS += "<td>" + "<table width='100%' cellspacing='1' cellpadding='1'>" + "<tbody>"
							+ "<tr class='celdaColumna'>"
							+ "<td align='center' class='tablaHistDatos' style='width:100%'>Sin Tarea Sumativa</td>"
							+ "</tr>" + "</tbody>" + "</table>" + "</td>";

					Data.append("<td>-</td>");
					Data.append("<td>-</td>");
					Data.append("<td>-</td>");
					Data.append("<td>-</td>");
					Data.append("<td>-</td>");
				}

				RESULTS += "</tr>";
				Data.append("</tr>");

				count++;
			}

			rSet.close();

			selectQuery.close();

			RESULTS += "</tbody>";
			RESULTS += "</table>";

			Data.append("</table>");

			RESULTS += "<div id='dvData' style='display:none;'>" + Data.toString() + "</div>";

		} catch (java.sql.SQLException sE) {

			RESULTS += "java.sql.SQLException sE: " + sE.toString();

		} catch (ConnectionNotAvailableException cE) {

			RESULTS += "ConnectionNotAvailableException cE :" + cE.toString();
		} finally {

			if (conn != null) {
				cManager.releaseConnection(conn);
			}
		}

		return RESULTS;
	}

	public String getInstructorAccess(String Sede, int page, String modalidad, String CourseFilter) {

		ConnectionManager cManager = null;
		Connection conn = null;
		PreparedStatement selectQuery = null;

		String RESULTS = "";
		StringBuilder TableData = new StringBuilder();
		String QueryText = "";
		try {

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			// GET
			// ALLCOURSES/INSTRUCTORS/NUM_STUDENTS/HAS_ACCESSED_COURSES/LAST_ACCESS/TIME_SPENT/START_DATE/WEEK 1-9_DATES
			
			int firstRow = (page - 1) * pageSize + 1;
			int lastRow = (page * pageSize) + 1;

			TableData.append("SELECT * FROM ( SELECT a.*, rownum rowcount FROM (");
			TableData.append("SELECT ");
			TableData.append(
					" CM.PK1 \"COURSE_PK1\",US.PK1 \"USER_PK1\", CONCAT(CONCAT(CM.COURSE_ID,'-'), CM.COURSE_NAME) \"CURSO\",");
			TableData.append(
					" (SELECT COUNT(*) FROM COURSE_USERS CUU WHERE CUU.CRSMAIN_PK1 = CM.PK1 AND CUU.ROLE = 'S') \"ESTUDIANTES\",");
			TableData.append(" US.LASTNAME, US.FIRSTNAME, US.EMAIL,US.STUDENT_ID,");
			TableData.append(" CASE");
			TableData.append(
					"     WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'NO'");
			TableData.append("     ELSE 'SI'");
			TableData.append(" END \"ACCEDIO AL CURSO\",");
			TableData.append(" CASE");
			TableData.append(
					"     WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'NUNCA'");
			TableData.append("     ELSE");
			TableData.append("     CONCAT((TRUNC(SYSDATE)-TRUNC(CU.LAST_ACCESS_DATE)),' días')");
			TableData.append(" END \"LAST_ACCESS\",");
			TableData.append(" CASE");
			TableData.append(
					"     WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 0");
			TableData.append(
					"     ELSE (SELECT SUM(COURSE_ACCESS_MINUTES) FROM ODS_AA_COURSE_ACTIVITY WHERE USER_PK1 = US.PK1 AND COURSE_PK1 = CM.PK1)");
			TableData.append(" END \"TIME\",");
			TableData.append(" CM.START_DATE \"FECHA INICIAL\",");
			TableData.append(" CM.START_DATE + 7 \"Semana 1\",");
			TableData.append(" CM.START_DATE + 14 \"Semana 2\",");
			TableData.append(" CM.START_DATE + 21 \"Semana 3\",");
			TableData.append(" CM.START_DATE + 28 \"Semana 4\",");
			TableData.append(" CM.START_DATE + 35 \"Semana 5\",");
			TableData.append(" CM.START_DATE + 42 \"Semana 6\",");
			TableData.append(" CM.START_DATE + 49 \"Semana 7\",");
			TableData.append(" CM.START_DATE + 56 \"Semana 8\",");
			TableData.append(" CM.START_DATE + 63 \"Semana 9\",");
			TableData.append(" TRUNC(CM.END_DATE) AS \"END_DATE\", ");
			TableData.append(" substr(cm.course_id,instr(cm.course_id,'-',-1) + 1,length(cm.course_id)) \"Modalidad\", ");
			TableData.append(" CS.SEDE ");
			
			TableData.append("FROM COURSE_MAIN CM ");
			TableData.append("INNER JOIN COURSE_USERS CU ON CU.CRSMAIN_PK1 = CM.PK1 ");
			TableData.append("INNER JOIN USERS US ON US.PK1 = CU.USERS_PK1 ");
			if(Sede.equals("Todo")) {
				TableData.append(" INNER JOIN (");
				TableData.append(" select sede.name as sede, course_id ");
				TableData.append(" from domain_course_coll_uid dom_crs inner join course_main course on course.pk1 = dom_crs.course_main_pk1 ");
				TableData.append(" INNER JOIN domain on domain.pk1 = dom_crs.domain_pk1 ");
				TableData.append(" inner join mi_node modulo  on modulo.domain_pk1 = domain.pk1 ");
				TableData.append(" inner join mi_node semestre on semestre.pk1 = modulo.parent_pk1 ");
				TableData.append(" inner join mi_node anio on anio.pk1 = semestre.parent_pk1 ");
				TableData.append(" inner join mi_node carrera on carrera.pk1 = anio.parent_pk1 ");
				TableData.append(" inner join mi_node escuela on escuela.pk1 = carrera.parent_pk1 ");
				TableData.append(" inner join mi_node sede on sede.pk1 = escuela.parent_pk1 ");
				TableData.append(" ) CS ON CS.COURSE_ID = CM.COURSE_ID ");
			} else {
				TableData.append(" INNER JOIN (");
				TableData.append(" select sede.name as sede, course_id ");
				TableData.append(" from domain_course_coll_uid dom_crs inner join course_main course on course.pk1 = dom_crs.course_main_pk1 ");
				TableData.append(" INNER JOIN domain on domain.pk1 = dom_crs.domain_pk1 ");
				TableData.append(" inner join mi_node modulo  on modulo.domain_pk1 = domain.pk1 ");
				TableData.append(" inner join mi_node semestre on semestre.pk1 = modulo.parent_pk1 ");
				TableData.append(" inner join mi_node anio on anio.pk1 = semestre.parent_pk1 ");
				TableData.append(" inner join mi_node carrera on carrera.pk1 = anio.parent_pk1 ");
				TableData.append(" inner join mi_node escuela on escuela.pk1 = carrera.parent_pk1 ");
				TableData.append(" inner join mi_node sede on sede.pk1 = escuela.parent_pk1 ");
				TableData.append(" WHERE sede.name = '"+Sede+"') CS ON CS.COURSE_ID = CM.COURSE_ID ");
			}
			TableData.append("WHERE CU.ROLE = 'Docente' ");
			TableData.append("AND CM.ROW_STATUS = 0 AND CU.ROW_STATUS = 0");
	
			if (!CourseFilter.equals("Todo")){
				
				TableData.append("AND CM.COURSE_ID LIKE '"+ CourseFilter +"' ");
			}
			
			TableData.append(
					"AND NVL(CM.END_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) != to_date('0001/01/01', 'yyyy-mm-dd') ");
			TableData.append(
					"AND NVL(CM.START_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) != to_date('0001/01/01', 'yyyy-mm-dd') ");
			TableData.append("ORDER BY CM.PK1 DESC ");
			TableData.append(") a WHERE rownum < " + lastRow + " ) WHERE rowcount >= " + firstRow);

			QueryText = TableData.toString();
			
			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			
			ResultSet rSet = selectQuery.executeQuery();

			RESULTS = "<table id='grid-basic' class='table table-condensed table-hover table-striped'>";
			RESULTS += "<thead>";
			RESULTS += "<tr>";

			String ColumnName = "";

			ColumnName = "CURSO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";
			
			ColumnName = "MODALIDAD";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEDE";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";
			
			ColumnName = "ESTUDIANTES";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "APELLIDOS";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "NOMBRES";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "RUT";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "EMAIL";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "ACCEDIO AL CURSO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "ULTIMO INGRESO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "TIEMPO DEDICADO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";
			
			ColumnName = "FECHA DE INICIO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 1";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 2";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 3";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 4";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 5";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 6";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 7";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 8";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 9";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			RESULTS += "</tr>";
			RESULTS += "</thead>";
			RESULTS += "<tbody>";

			String CoursePk1 = "";
			String InstructorsPk1 = "";
			String CourseName = "";
			String Num_Stu = "";
			String LastName = "";
			String FirstName = "";
			String Email = "";
			String User_Id = "";
			String Has_Access = "";
			String Last_Access = "";
			String TimeSpent = "";
			Date Start_Date = null;
			Date Week1 = null;
			Date Week2 = null;
			Date Week3 = null;
			Date Week4 = null;
			Date Week5 = null;
			Date Week6 = null;
			Date Week7 = null;
			Date Week8 = null;
			Date Week9 = null;
			Date End_Date = null;
			String Sede_Curso = "";
			String Week1Status = "";
			String Week2Status = "";
			String Week3Status = "";
			String Week4Status = "";
			String Week5Status = "";
			String Week6Status = "";
			String Week7Status = "";
			String Week8Status = "";
			String Week9Status = "";
			Date TempDate = null;
			
			//New Variables
			String Start_Date_Text = "";
			String Modality_Text = "";
			String Headquarter_Text = "";
			//New Variables
			
			long count = 0L;

			ResultSet TemprSet = null;
			PreparedStatement TempselectQuery = null;

			ArrayList<Date> Access_Dates = null;

			while (rSet.next()) {

				CoursePk1 = rSet.getString("COURSE_PK1");
				InstructorsPk1 = rSet.getString("USER_PK1");
				CourseName = rSet.getString("CURSO");
				Num_Stu = rSet.getString("ESTUDIANTES");
				LastName = rSet.getString("LASTNAME");
				FirstName = rSet.getString("FIRSTNAME");
				Email = rSet.getString("EMAIL");
				User_Id = rSet.getString("STUDENT_ID");
				Has_Access = rSet.getString("ACCEDIO AL CURSO");
				Last_Access = rSet.getString("LAST_ACCESS");
				TimeSpent = rSet.getString("TIME");
				Start_Date = rSet.getDate("FECHA INICIAL");
				Week1 = rSet.getDate("Semana 1");
				Week2 = rSet.getDate("Semana 2");
				Week3 = rSet.getDate("Semana 3");
				Week4 = rSet.getDate("Semana 4");
				Week5 = rSet.getDate("Semana 5");
				Week6 = rSet.getDate("Semana 6");
				Week7 = rSet.getDate("Semana 7");
				Week8 = rSet.getDate("Semana 8");
				Week9 = rSet.getDate("Semana 9");
				End_Date = rSet.getDate("END_DATE");
				
				if(TimeSpent == null){
					
					TimeSpent = "0";
				}

				if(Has_Access == null){
					
					Has_Access = "NO";
				}

				if (!TimeSpent.equals("0")) {

					TimeSpent = parseTime(TimeSpent);
				} else {

					TimeSpent += " segs";
				}
				
				//New Columns Code
				Start_Date_Text = rSet.getString("FECHA INICIAL").split(" ")[0];
				Modality_Text = rSet.getString("Modalidad");
				Headquarter_Text = rSet.getString("SEDE");
				//New Columns Code

				RESULTS += "<tr>";
				RESULTS += "<td><a target='_blank' href='/webapps/blackboard/execute/courseMain?course_id="
						+ rSet.getString("COURSE_PK1") + "'>" + CourseName + "</a></td>";
				RESULTS += "<td>" + Modality_Text + "</td>";
				RESULTS += "<td>" + Headquarter_Text + "</td>";
				RESULTS += "<td>" + Num_Stu + "</td>";
				RESULTS += "<td>" + LastName + "</td>";
				RESULTS += "<td>" + FirstName + "</td>";
				RESULTS += "<td>" + User_Id + "</td>";
				RESULTS += "<td>" + Email + "</td>";
				RESULTS += "<td>" + Has_Access + "</td>";
				RESULTS += "<td>" + Last_Access + "</td>";
				RESULTS += "<td>" + TimeSpent + "</td>";
				RESULTS += "<td>" + Start_Date_Text + "</td>";

				if (!Has_Access.equals("NO")) {

					TableData = new StringBuilder();
					TableData.append(
							"SELECT DISTINCT TRUNC(TIMESTAMP) \"DATE\" FROM ACTIVITY_ACCUMULATOR ");
					TableData.append("WHERE USER_PK1 = " + InstructorsPk1 + " ");
					TableData.append("AND COURSE_PK1 = " + CoursePk1 + " ");

					TempselectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
							ResultSet.CONCUR_READ_ONLY);

					TemprSet = TempselectQuery.executeQuery();

					Access_Dates = new ArrayList<Date>();

					while (TemprSet.next()) {

						Access_Dates.add(TemprSet.getDate(1));
					}

					TemprSet.close();

					TempselectQuery.close();

					Week1Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week2Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week3Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week4Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week5Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week6Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week7Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week8Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week9Status = "<td style=\"color: red;\">Sin Participacion</td>";

					for (int i = 0; i < Access_Dates.size(); i++) {

						TempDate = Access_Dates.get(i);

						if (TempDate.before(End_Date)) {
							//SEMANA 1
							if(TempDate.compareTo(Start_Date) >= 0 && TempDate.compareTo(Week1) < 0) {
								Week1Status = "<td>Con Participacion</td>";
							}
							//SEMANA 2
							if(TempDate.compareTo(Week1) >= 0 && TempDate.compareTo(Week2) < 0) {
								Week2Status = "<td>Con Participacion</td>";
							}
							//SEMANA 3
							if(TempDate.compareTo(Week2) >= 0 && TempDate.compareTo(Week3) < 0) {
								Week3Status = "<td>Con Participacion</td>";
							}
							//SEMANA 4
							if(TempDate.compareTo(Week3) >= 0 && TempDate.compareTo(Week4) < 0) {
								Week4Status = "<td>Con Participacion</td>";
							}
							//SEMANA 5
							if(TempDate.compareTo(Week4) >= 0 && TempDate.compareTo(Week5) < 0) {
								Week5Status = "<td>Con Participacion</td>";
							}
							//SEMANA 6
							if(TempDate.compareTo(Week5) >= 0 && TempDate.compareTo(Week6) < 0) {
								Week6Status = "<td>Con Participacion</td>";
							}
							//SEMANA 7
							if(TempDate.compareTo(Week6) >= 0 && TempDate.compareTo(Week7) < 0) {
								Week7Status = "<td>Con Participacion</td>";
							}
							//SEMANA 8
							if(TempDate.compareTo(Week7) >= 0 && TempDate.compareTo(Week8) < 0) {
								Week8Status = "<td>Con Participacion</td>";
							}
							//SEMANA 9
							if(TempDate.compareTo(Week8) >= 0 && TempDate.compareTo(Week9) <= 0) {
								Week9Status = "<td>Con Participacion</td>";
							}
						}
					}

					RESULTS += Week1Status;
					RESULTS += Week2Status;
					RESULTS += Week3Status;
					RESULTS += Week4Status;
					RESULTS += Week5Status;
					RESULTS += Week6Status;
					RESULTS += Week7Status;
					RESULTS += Week8Status;
					RESULTS += Week9Status;
				} else {

					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
				}

				RESULTS += "</tr>";

				count++;
			}

			rSet.close();

			selectQuery.close();

			RESULTS += "</tbody>";
			RESULTS += "</table>";

			//RESULTS += "<label>Total: " + count + "</label>";

		} catch (java.sql.SQLException sE) {

			RESULTS += "java.sql.SQLException sE: " + sE.toString();
			System.out.println("Error at getInstructorAccess, Query " + QueryText);

		} catch (ConnectionNotAvailableException cE) {

			RESULTS += "ConnectionNotAvailableException cE :" + cE.toString();
		} finally {

			if (conn != null) {
				cManager.releaseConnection(conn);
			}
		}

		return RESULTS;
	}
	
	public String getInstructorAccessWholeReport(String Sede, String modalidad, String CourseFilter) {

		ConnectionManager cManager = null;
		Connection conn = null;
		PreparedStatement selectQuery = null;

		String RESULTS = "";
		StringBuilder TableData = new StringBuilder();
		String QueryText = "";
		try {

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			// GET
			// ALLCOURSES/INSTRUCTORS/NUM_STUDENTS/HAS_ACCESSED_COURSES/LAST_ACCESS/TIME_SPENT/START_DATE/WEEK1-9_DATES
			
			TableData.append("SELECT ");
			TableData.append(
					" CM.PK1 \"COURSE_PK1\",US.PK1 \"USER_PK1\", CONCAT(CONCAT(CM.COURSE_ID,'-'), CM.COURSE_NAME) \"CURSO\",");
			TableData.append(
					" (SELECT COUNT(*) FROM COURSE_USERS CUU WHERE CUU.CRSMAIN_PK1 = CM.PK1 AND CUU.ROLE = 'S') \"ESTUDIANTES\",");
			TableData.append(" US.LASTNAME, US.FIRSTNAME, US.EMAIL,US.STUDENT_ID,");
			TableData.append(" CASE");
			TableData.append(
					"     WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'NO'");
			TableData.append("     ELSE 'SI'");
			TableData.append(" END \"ACCEDIO AL CURSO\",");
			TableData.append(" CASE");
			TableData.append(
					"     WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'NUNCA'");
			TableData.append("     ELSE");
			TableData.append("     CONCAT((TRUNC(SYSDATE)-TRUNC(CU.LAST_ACCESS_DATE)),' días')");
			TableData.append(" END \"LAST_ACCESS\",");
			TableData.append(" CASE");
			TableData.append(
					"     WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 0");
			TableData.append(
					"     ELSE (SELECT SUM(COURSE_ACCESS_MINUTES) FROM ODS_AA_COURSE_ACTIVITY WHERE USER_PK1 = US.PK1 AND COURSE_PK1 = CM.PK1)");
			TableData.append(" END \"TIME\",");
			TableData.append(" CM.START_DATE \"FECHA INICIAL\",");
			TableData.append(" CM.START_DATE + 7 \"Semana 1\",");
			TableData.append(" CM.START_DATE + 14 \"Semana 2\",");
			TableData.append(" CM.START_DATE + 21 \"Semana 3\",");
			TableData.append(" CM.START_DATE + 28 \"Semana 4\",");
			TableData.append(" CM.START_DATE + 35 \"Semana 5\",");
			TableData.append(" CM.START_DATE + 42 \"Semana 6\",");
			TableData.append(" CM.START_DATE + 49 \"Semana 7\",");
			TableData.append(" CM.START_DATE + 56 \"Semana 8\",");
			TableData.append(" CM.START_DATE + 69 \"Semana 9\",");
			TableData.append(" TRUNC(CM.END_DATE) AS \"END_DATE\", ");
			
			//New Columns
			TableData.append(" substr(cm.course_id,instr(cm.course_id,'-',-1) + 1,length(cm.course_id)) \"Modalidad\", CS.SEDE ");
			//New Columns
			
			TableData.append("FROM COURSE_MAIN CM ");
			TableData.append("INNER JOIN COURSE_USERS CU ON CU.CRSMAIN_PK1 = CM.PK1 ");
			TableData.append("INNER JOIN USERS US ON US.PK1 = CU.USERS_PK1 ");
			if(Sede.equals("Todo")) {
				TableData.append(" INNER JOIN (");
				TableData.append(" select sede.name as sede, course_id ");
				TableData.append(" from domain_course_coll_uid dom_crs inner join course_main course on course.pk1 = dom_crs.course_main_pk1 ");
				TableData.append(" INNER JOIN domain on domain.pk1 = dom_crs.domain_pk1 ");
				TableData.append(" inner join mi_node modulo  on modulo.domain_pk1 = domain.pk1 ");
				TableData.append(" inner join mi_node semestre on semestre.pk1 = modulo.parent_pk1 ");
				TableData.append(" inner join mi_node anio on anio.pk1 = semestre.parent_pk1 ");
				TableData.append(" inner join mi_node carrera on carrera.pk1 = anio.parent_pk1 ");
				TableData.append(" inner join mi_node escuela on escuela.pk1 = carrera.parent_pk1 ");
				TableData.append(" inner join mi_node sede on sede.pk1 = escuela.parent_pk1 ");
				TableData.append(" ) CS ON CS.COURSE_ID = CM.COURSE_ID ");
			} else {
				TableData.append(" INNER JOIN (");
				TableData.append(" select sede.name as sede, course_id ");
				TableData.append(" from domain_course_coll_uid dom_crs inner join course_main course on course.pk1 = dom_crs.course_main_pk1 ");
				TableData.append(" INNER JOIN domain on domain.pk1 = dom_crs.domain_pk1 ");
				TableData.append(" inner join mi_node modulo  on modulo.domain_pk1 = domain.pk1 ");
				TableData.append(" inner join mi_node semestre on semestre.pk1 = modulo.parent_pk1 ");
				TableData.append(" inner join mi_node anio on anio.pk1 = semestre.parent_pk1 ");
				TableData.append(" inner join mi_node carrera on carrera.pk1 = anio.parent_pk1 ");
				TableData.append(" inner join mi_node escuela on escuela.pk1 = carrera.parent_pk1 ");
				TableData.append(" inner join mi_node sede on sede.pk1 = escuela.parent_pk1 ");
				TableData.append(" WHERE sede.name = '"+Sede+"') CS ON CS.COURSE_ID = CM.COURSE_ID ");
			}
			TableData.append("WHERE CU.ROLE = 'Docente' ");
			TableData.append("AND CM.ROW_STATUS = 0 AND CU.ROW_STATUS = 0");
			
			if (!CourseFilter.equals("Todo")){
				
				TableData.append("AND CM.COURSE_ID LIKE '"+ CourseFilter +"' ");
			}
			
			//REMOVE AND REPLACE BY THE NEW ONE

			TableData.append(
					"AND NVL(CM.END_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) != to_date('0001/01/01', 'yyyy-mm-dd') ");
			TableData.append(
					"AND NVL(CM.START_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) != to_date('0001/01/01', 'yyyy-mm-dd') ");
			TableData.append("ORDER BY CM.PK1 DESC ");

			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			
			QueryText = TableData.toString();

			ResultSet rSet = selectQuery.executeQuery();

			RESULTS = "<table border = 1>";
			RESULTS += "<thead>";
			RESULTS += "<tr>";

			String ColumnName = "";

			ColumnName = "CURSO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";
			
			ColumnName = "MODALIDAD";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEDE";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";
			
			ColumnName = "ESTUDIANTES";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "APELLIDOS";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "NOMBRES";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "RUT";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "EMAIL";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "ACCEDIO AL CURSO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "ULTIMO INGRESO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "TIEMPO DEDICADO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";
			
			ColumnName = "FECHA DE INICIO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 1";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 2";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 3";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 4";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 5";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 6";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 7";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 8";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "SEMANA 9";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			RESULTS += "</tr>";
			RESULTS += "</thead>";
			RESULTS += "<tbody>";

			String CoursePk1 = "";
			String InstructorsPk1 = "";
			String CourseName = "";
			String Num_Stu = "";
			String LastName = "";
			String FirstName = "";
			String Email = "";
			String User_Id = "";
			String Has_Access = "";
			String Last_Access = "";
			String TimeSpent = "";
			Date Start_Date = null;
			Date Week1 = null;
			Date Week2 = null;
			Date Week3 = null;
			Date Week4 = null;
			Date Week5 = null;
			Date Week6 = null;
			Date Week7 = null;
			Date Week8 = null;
			Date Week9 = null;
			Date End_Date = null;
			String Week1Status = "";
			String Week2Status = "";
			String Week3Status = "";
			String Week4Status = "";
			String Week5Status = "";
			String Week6Status = "";
			String Week7Status = "";
			String Week8Status = "";
			String Week9Status = "";
			Date TempDate = null;
			
			//New Variables
			String Start_Date_Text = "";
			String Modality_Text = "";
			String Headquarter_Text = "";
			//New Variables
			
			long count = 0L;

			ResultSet TemprSet = null;
			PreparedStatement TempselectQuery = null;

			ArrayList<Date> Access_Dates = null;

			while (rSet.next()) {
				
				CoursePk1 = rSet.getString("COURSE_PK1");
				InstructorsPk1 = rSet.getString("USER_PK1");
				CourseName = rSet.getString("CURSO");
				Num_Stu = rSet.getString("ESTUDIANTES");
				LastName = rSet.getString("LASTNAME");
				FirstName = rSet.getString("FIRSTNAME");
				Email = rSet.getString("EMAIL");
				User_Id = rSet.getString("STUDENT_ID");
				Has_Access = rSet.getString("ACCEDIO AL CURSO");
				Last_Access = rSet.getString("LAST_ACCESS");
				TimeSpent = rSet.getString("TIME");
				Start_Date = rSet.getDate("FECHA INICIAL");
				Week1 = rSet.getDate("Semana 1");
				Week2 = rSet.getDate("Semana 2");
				Week3 = rSet.getDate("Semana 3");
				Week4 = rSet.getDate("Semana 4");
				Week5 = rSet.getDate("Semana 5");
				Week6 = rSet.getDate("Semana 6");
				Week7 = rSet.getDate("Semana 7");
				Week8 = rSet.getDate("Semana 8");
				Week9 = rSet.getDate("Semana 9");
				End_Date = rSet.getDate("END_DATE");
				
				if(TimeSpent == null){
					
					TimeSpent = "0";
				}

				if(Has_Access == null){
					
					Has_Access = "NO";
				}

				if (!TimeSpent.equals("0")) {

					TimeSpent = parseTime(TimeSpent);
				} else {

					TimeSpent += " segs";
				}
				
				//New Columns Code
				Start_Date_Text = rSet.getString("FECHA INICIAL").split(" ")[0];
				Modality_Text = rSet.getString("Modalidad");
				Headquarter_Text = rSet.getString("SEDE");
				//New Columns Code

				RESULTS += "<tr>";
				RESULTS += "<td><a target='_blank' href='/webapps/blackboard/execute/courseMain?course_id="
						+ rSet.getString(1) + "'>" + CourseName + "</a></td>";
				RESULTS += "<td>" + Modality_Text + "</td>";
				RESULTS += "<td>" + Headquarter_Text + "</td>";
				RESULTS += "<td>" + Num_Stu + "</td>";
				RESULTS += "<td>" + LastName + "</td>";
				RESULTS += "<td>" + FirstName + "</td>";
				RESULTS += "<td>" + User_Id + "</td>";
				RESULTS += "<td>" + Email + "</td>";
				RESULTS += "<td>" + Has_Access + "</td>";
				RESULTS += "<td>" + Last_Access + "</td>";
				RESULTS += "<td>" + TimeSpent + "</td>";
				RESULTS += "<td>" + Start_Date_Text + "</td>";

				if (!Has_Access.equals("NO")) {

					// GET ALL ACCESS DATES FROM SPECIFIC USER TO SPECIFIC
					// COURSE_USERS
					TableData = new StringBuilder();
					TableData.append(
							"SELECT DISTINCT TRUNC(INITIAL_DATETIME_ACCESS) \"DATE\" FROM ODS_AA_COURSE_ACTIVITY ");
					TableData.append("WHERE USER_PK1 = " + InstructorsPk1 + " ");
					TableData.append("AND COURSE_PK1 = " + CoursePk1 + " ");

					TempselectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
							ResultSet.CONCUR_READ_ONLY);

					TemprSet = TempselectQuery.executeQuery();

					Access_Dates = new ArrayList<Date>();

					while (TemprSet.next()) {

						Access_Dates.add(TemprSet.getDate(1));
					}

					TemprSet.close();

					TempselectQuery.close();

					Week1Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week2Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week3Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week4Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week5Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week6Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week7Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week8Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week9Status = "<td style=\"color: red;\">Sin Participacion</td>";

					for (int i = 0; i < Access_Dates.size(); i++) {

						TempDate = Access_Dates.get(i);

						if (TempDate.before(End_Date)) {

							if (TempDate.after(Start_Date) && TempDate.before(Week1)) {

								Week1Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week1) && TempDate.before(Week2)) {

								Week2Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week2) && TempDate.before(Week3)) {

								Week3Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week3) && TempDate.before(Week4)) {

								Week4Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week4) && TempDate.before(Week5)) {

								Week5Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week5) && TempDate.before(Week6)) {

								Week6Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week6) && TempDate.before(Week7)) {

								Week7Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week7) && TempDate.before(Week8)) {

								Week8Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week8) && TempDate.before(Week9)) {

								Week9Status = "<td>Con Participacion</td>";
							}
						}
					}

					RESULTS += Week1Status;
					RESULTS += Week2Status;
					RESULTS += Week3Status;
					RESULTS += Week4Status;
					RESULTS += Week5Status;
					RESULTS += Week6Status;
					RESULTS += Week7Status;
					RESULTS += Week8Status;
					RESULTS += Week9Status;
				} else {

					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
				}

				RESULTS += "</tr>";

				count++;
			}

			rSet.close();

			selectQuery.close();

			RESULTS += "</tbody>";
			RESULTS += "</table>";

			//RESULTS += "<label>Total: " + count + "</label>";

		} catch (java.sql.SQLException sE) {

			RESULTS += "java.sql.SQLException sE: " + sE.toString();

		} catch (ConnectionNotAvailableException cE) {

			RESULTS += "ConnectionNotAvailableException cE :" + cE.toString();
		} finally {

			if (conn != null) {
				cManager.releaseConnection(conn);
			}
		}

		return RESULTS;
	}
	
	public String getInstructorAccessReportPerInterval(String Headquarter, String modalidad, int firstRow, int lastRow) {

		ConnectionManager cManager = null;
		Connection conn = null;
		PreparedStatement selectQuery = null;

		String RESULTS = "START";
		
		int RowCount = 0;

		try {

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			String CoursePk1 = "";
			String InstructorsPk1 = "";
			String CourseName = "";
			String Num_Stu = "";
			String LastName = "";
			String FirstName = "";
			String Email = "";
			String User_Id = "";
			String Has_Access = "";
			String Last_Access = "";
			String TimeSpent = "";
			Date Start_Date = null;
			Date Week1 = null;
			Date Week2 = null;
			Date Week3 = null;
			Date Week4 = null;
			Date Week5 = null;
			Date Week6 = null;
			Date Week7 = null;
			Date Week8 = null;
			Date Week9 = null;
			Date End_Date = null;
			String Week1Status = "";
			String Week2Status = "";
			String Week3Status = "";
			String Week4Status = "";
			String Week5Status = "";
			String Week6Status = "";
			String Week7Status = "";
			String Week8Status = "";
			String Week9Status = "";
			Date TempDate = null;

			long count = 0L;

			ResultSet TemprSet = null;
			PreparedStatement TempselectQuery = null;

			ArrayList<Date> Access_Dates = null;
			
			StringBuilder TableData = new StringBuilder();
			TableData.append("SELECT * FROM ( SELECT a.*, rownum rowcount FROM (");
			TableData.append("SELECT ");
			TableData.append(
					" CM.PK1 \"COURSE_PK1\",US.PK1 \"USER_PK1\", CONCAT(CONCAT(CM.COURSE_ID,'-'), CM.COURSE_NAME) \"CURSO\",");
			TableData.append(
					" (SELECT COUNT(*) FROM COURSE_USERS CUU WHERE CUU.CRSMAIN_PK1 = CM.PK1 AND CUU.ROLE = 'S') \"ESTUDIANTES\",");
			TableData.append(" US.LASTNAME, US.FIRSTNAME, US.EMAIL,US.STUDENT_ID,");
			TableData.append(" CASE");
			TableData.append(
					"     WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'NO'");
			TableData.append("     ELSE 'SI'");
			TableData.append(" END \"ACCEDIO AL CURSO\",");
			TableData.append(" CASE");
			TableData.append(
					"     WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'NUNCA'");
			TableData.append("     ELSE");
			TableData.append("     CONCAT((TRUNC(SYSDATE)-TRUNC(CU.LAST_ACCESS_DATE)),' días')");
			TableData.append(" END \"LAST_ACCESS\",");
			TableData.append(" CASE");
			TableData.append(
					"     WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 0");
			TableData.append(
					"     ELSE (SELECT SUM(COURSE_ACCESS_MINUTES) FROM ODS_AA_COURSE_ACTIVITY WHERE USER_PK1 = US.PK1 AND COURSE_PK1 = CM.PK1)");
			TableData.append(" END \"TIME\",");
			TableData.append(" CM.START_DATE \"FECHA INICIAL\",");
			TableData.append(" CM.START_DATE + 7 \"Semana 1\",");
			TableData.append(" CM.START_DATE + 14 \"Semana 2\",");
			TableData.append(" CM.START_DATE + 21 \"Semana 3\",");
			TableData.append(" CM.START_DATE + 28 \"Semana 4\",");
			TableData.append(" CM.START_DATE + 35 \"Semana 5\",");
			TableData.append(" CM.START_DATE + 42 \"Semana 6\",");
			TableData.append(" CM.START_DATE + 49 \"Semana 7\",");
			TableData.append(" CM.START_DATE + 56 \"Semana 8\",");
			TableData.append(" CM.START_DATE + 69 \"Semana 9\",");
			TableData.append(" TRUNC(CM.END_DATE) AS \"END_DATE\" ");
			TableData.append("FROM COURSE_MAIN CM ");
			TableData.append("INNER JOIN COURSE_USERS CU ON CU.CRSMAIN_PK1 = CM.PK1 ");
			TableData.append("INNER JOIN USERS US ON US.PK1 = CU.USERS_PK1 ");
			TableData.append("INNER JOIN INSTITUTION_ROLES IR ON US.INSTITUTION_ROLES_PK1 = IR.PK1 ");
			TableData.append("WHERE CU.ROLE = 'Docente' ");
			TableData.append("AND CM.ROW_STATUS = 0 AND CU.ROW_STATUS = 0");
			
			if (!modalidad.equals("Todo")){
				
				TableData.append("AND CM.COURSE_ID LIKE '%-"+ modalidad +"'");
			}

			if (!Headquarter.equals("Todo")) {

				TableData.append("AND US.B_PHONE_1 = '" + Headquarter + "' ");
			}
			TableData.append(
					"AND NVL(CM.END_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) != to_date('0001/01/01', 'yyyy-mm-dd') ");
			TableData.append(
					"AND NVL(CM.START_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) != to_date('0001/01/01', 'yyyy-mm-dd') ");
			TableData.append("ORDER BY CM.PK1 DESC ");
			TableData.append(") a WHERE rownum <= " + lastRow + " ) WHERE rowcount > " + firstRow);

			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			//RESULTS += "<br>" + TableData.toString();
			
			ResultSet rSet = selectQuery.executeQuery();
			
			while (rSet.next()) {
				
				//RESULTS+="<br> Entro Al While";

				CoursePk1 = rSet.getString(1);
				InstructorsPk1 = rSet.getString(2);
				CourseName = rSet.getString(3);
				Num_Stu = rSet.getString(4);
				LastName = rSet.getString(5);
				FirstName = rSet.getString(6);
				Email = rSet.getString(7);
				User_Id = rSet.getString(8);
				Has_Access = rSet.getString(9);
				Last_Access = rSet.getString(10);
				TimeSpent = rSet.getString(11);
				Start_Date = rSet.getDate(12);
				Week1 = rSet.getDate(13);
				Week2 = rSet.getDate(14);
				Week3 = rSet.getDate(15);
				Week4 = rSet.getDate(16);
				Week5 = rSet.getDate(17);
				Week6 = rSet.getDate(18);
				Week7 = rSet.getDate(19);
				Week8 = rSet.getDate(20);
				Week9 = rSet.getDate(21);
				End_Date = rSet.getDate(22);
				
				if(TimeSpent == null){
					
					TimeSpent = "0";
				}

				if(Has_Access == null){
					
					Has_Access = "NO";
				}

				if (!TimeSpent.equals("0")) {

					TimeSpent = parseTime(TimeSpent);
				} else {

					TimeSpent += " segs";
				}

				RESULTS += "<tr>";
				RESULTS += "<td><a target='_blank' href='/webapps/blackboard/execute/courseMain?course_id="
						+ rSet.getString(1) + "'>" + CourseName + "</a></td>";
				RESULTS += "<td>" + Num_Stu + "</td>";
				RESULTS += "<td>" + LastName + "</td>";
				RESULTS += "<td>" + FirstName + "</td>";
				RESULTS += "<td>" + User_Id + "</td>";
				RESULTS += "<td>" + Email + "</td>";
				RESULTS += "<td>" + Has_Access + "</td>";
				RESULTS += "<td>" + Last_Access + "</td>";
				RESULTS += "<td>" + TimeSpent + "</td>";

				if (!Has_Access.equals("NO")) {

					// GET ALL ACCESS DATES FROM SPECIFIC USER TO SPECIFIC
					// COURSE_USERS
					TableData = new StringBuilder();
					TableData.append(
							"SELECT DISTINCT TRUNC(INITIAL_DATETIME_ACCESS) \"DATE\" FROM ODS_AA_COURSE_ACTIVITY ");
					TableData.append("WHERE USER_PK1 = " + InstructorsPk1 + " ");
					TableData.append("AND COURSE_PK1 = " + CoursePk1 + " ");

					TempselectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
							ResultSet.CONCUR_READ_ONLY);

					TemprSet = TempselectQuery.executeQuery();

					Access_Dates = new ArrayList<Date>();

					while (TemprSet.next()) {

						Access_Dates.add(TemprSet.getDate(1));
					}

					TemprSet.close();

					TempselectQuery.close();

					Week1Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week2Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week3Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week4Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week5Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week6Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week7Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week8Status = "<td style=\"color: red;\">Sin Participacion</td>";
					Week9Status = "<td style=\"color: red;\">Sin Participacion</td>";

					for (int i = 0; i < Access_Dates.size(); i++) {

						TempDate = Access_Dates.get(i);

						if (TempDate.before(End_Date)) {

							if (TempDate.after(Start_Date) && TempDate.before(Week1)) {

								Week1Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week1) && TempDate.before(Week2)) {

								Week2Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week2) && TempDate.before(Week3)) {

								Week3Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week3) && TempDate.before(Week4)) {

								Week4Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week4) && TempDate.before(Week5)) {

								Week5Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week5) && TempDate.before(Week6)) {

								Week6Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week6) && TempDate.before(Week7)) {

								Week7Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week7) && TempDate.before(Week8)) {

								Week8Status = "<td>Con Participacion</td>";
							}
							if (TempDate.after(Week8) && TempDate.before(Week9)) {

								Week9Status = "<td>Con Participacion</td>";
							}
						}
					}

					RESULTS += Week1Status;
					RESULTS += Week2Status;
					RESULTS += Week3Status;
					RESULTS += Week4Status;
					RESULTS += Week5Status;
					RESULTS += Week6Status;
					RESULTS += Week7Status;
					RESULTS += Week8Status;
					RESULTS += Week9Status;
					
				} else {

					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
					RESULTS += "<td style=\"color: red;\">Sin Participacion</td>";
				}

				RESULTS += "</tr>";
			}

			rSet.close();

			selectQuery.close();
			
		} catch (java.sql.SQLException sE) {

			RESULTS += "java.sql.SQLException sE: " + sE.toString();

		} catch (ConnectionNotAvailableException cE) {

			RESULTS += "ConnectionNotAvailableException cE :" + cE.toString();
			
		} finally {

			if (conn != null) {
				cManager.releaseConnection(conn);
			}
		}

		return RESULTS;
	}
	
	public int getInstructorAccessRowCount(String Sede, String modalidad,String CourseFilter) {

		ConnectionManager cManager = null;
		Connection conn = null;
		PreparedStatement selectQuery = null;

		String RESULTS = "";
		
		int RowCount = 0;

		try {

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			// GET
			// ALLCOURSES/INSTRUCTORS/NUM_STUDENTS/HAS_ACCESSED_COURSES/LAST_ACCESS/TIME_SPENT/START_DATE/WEEK1-9_DATES

			StringBuilder TableData = new StringBuilder();
			TableData.append("SELECT COUNT(*) FROM ( ");
			TableData.append("SELECT ");
			TableData.append(
					" CM.PK1 \"COURSE_PK1\",US.PK1 \"USER_PK1\", CONCAT(CONCAT(CM.COURSE_ID,'-'), CM.COURSE_NAME) \"CURSO\",");
			TableData.append(
					" (SELECT COUNT(*) FROM COURSE_USERS CUU WHERE CUU.CRSMAIN_PK1 = CM.PK1 AND CUU.ROLE = 'S') \"ESTUDIANTES\",");
			TableData.append(" US.LASTNAME, US.FIRSTNAME, US.EMAIL,US.STUDENT_ID,");
			TableData.append(" CASE");
			TableData.append(
					"     WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'NO'");
			TableData.append("     ELSE 'SI'");
			TableData.append(" END \"ACCEDIO AL CURSO\",");
			TableData.append(" CASE");
			TableData.append(
					"     WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'NUNCA'");
			TableData.append("     ELSE");
			TableData.append("     CONCAT((TRUNC(SYSDATE)-TRUNC(CU.LAST_ACCESS_DATE)),' días')");
			TableData.append(" END \"LAST_ACCESS\",");
			TableData.append(" CASE");
			TableData.append(
					"     WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 0");
			TableData.append(
					"     ELSE (SELECT SUM(COURSE_ACCESS_MINUTES) FROM ODS_AA_COURSE_ACTIVITY WHERE USER_PK1 = US.PK1 AND COURSE_PK1 = CM.PK1)");
			TableData.append(" END \"TIME\",");
			TableData.append(" CM.START_DATE \"FECHA INICIAL\",");
			TableData.append(" CM.START_DATE + 7 \"Semana 1\",");
			TableData.append(" CM.START_DATE + 14 \"Semana 2\",");
			TableData.append(" CM.START_DATE + 21 \"Semana 3\",");
			TableData.append(" CM.START_DATE + 28 \"Semana 4\",");
			TableData.append(" CM.START_DATE + 35 \"Semana 5\",");
			TableData.append(" CM.START_DATE + 42 \"Semana 6\",");
			TableData.append(" CM.START_DATE + 49 \"Semana 7\",");
			TableData.append(" CM.START_DATE + 56 \"Semana 8\",");
			TableData.append(" CM.START_DATE + 63 \"Semana 9\",");
			TableData.append(" TRUNC(CM.END_DATE) AS \"END_DATE\", ");
			TableData.append(" substr(cm.course_id,instr(cm.course_id,'-',-1) + 1,length(cm.course_id)) \"Modalidad\", ");
			TableData.append(" CS.SEDE ");
			
			TableData.append("FROM COURSE_MAIN CM ");
			TableData.append("INNER JOIN COURSE_USERS CU ON CU.CRSMAIN_PK1 = CM.PK1 ");
			TableData.append("INNER JOIN USERS US ON US.PK1 = CU.USERS_PK1 ");
			if(Sede.equals("Todo")) {
				TableData.append(" INNER JOIN (");
				TableData.append(" select sede.name as sede, course_id ");
				TableData.append(" from domain_course_coll_uid dom_crs inner join course_main course on course.pk1 = dom_crs.course_main_pk1 ");
				TableData.append(" INNER JOIN domain on domain.pk1 = dom_crs.domain_pk1 ");
				TableData.append(" inner join mi_node modulo  on modulo.domain_pk1 = domain.pk1 ");
				TableData.append(" inner join mi_node semestre on semestre.pk1 = modulo.parent_pk1 ");
				TableData.append(" inner join mi_node anio on anio.pk1 = semestre.parent_pk1 ");
				TableData.append(" inner join mi_node carrera on carrera.pk1 = anio.parent_pk1 ");
				TableData.append(" inner join mi_node escuela on escuela.pk1 = carrera.parent_pk1 ");
				TableData.append(" inner join mi_node sede on sede.pk1 = escuela.parent_pk1 ");
				TableData.append(" ) CS ON CS.COURSE_ID = CM.COURSE_ID ");
			} else {
				TableData.append(" INNER JOIN (");
				TableData.append(" select sede.name as sede, course_id ");
				TableData.append(" from domain_course_coll_uid dom_crs inner join course_main course on course.pk1 = dom_crs.course_main_pk1 ");
				TableData.append(" INNER JOIN domain on domain.pk1 = dom_crs.domain_pk1 ");
				TableData.append(" inner join mi_node modulo  on modulo.domain_pk1 = domain.pk1 ");
				TableData.append(" inner join mi_node semestre on semestre.pk1 = modulo.parent_pk1 ");
				TableData.append(" inner join mi_node anio on anio.pk1 = semestre.parent_pk1 ");
				TableData.append(" inner join mi_node carrera on carrera.pk1 = anio.parent_pk1 ");
				TableData.append(" inner join mi_node escuela on escuela.pk1 = carrera.parent_pk1 ");
				TableData.append(" inner join mi_node sede on sede.pk1 = escuela.parent_pk1 ");
				TableData.append(" WHERE sede.name = '"+Sede+"') CS ON CS.COURSE_ID = CM.COURSE_ID ");
			}
			TableData.append("WHERE CU.ROLE = 'Docente' ");
			TableData.append("AND CM.ROW_STATUS = 0 AND CU.ROW_STATUS = 0");
	
			if (!CourseFilter.equals("Todo")){
				
				TableData.append("AND CM.COURSE_ID LIKE '"+ CourseFilter +"' ");
			}
			
			TableData.append(
					"AND NVL(CM.END_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) != to_date('0001/01/01', 'yyyy-mm-dd') ");
			TableData.append(
					"AND NVL(CM.START_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) != to_date('0001/01/01', 'yyyy-mm-dd') ");
			TableData.append("ORDER BY CM.PK1 DESC ");
			TableData.append(") RESULTS");

			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rSet = selectQuery.executeQuery();
			
			while (rSet.next()) {
				
				RowCount = rSet.getInt(1);
			}
			
			rSet.close();

			selectQuery.close();
			
			

		} catch (java.sql.SQLException sE) {

			RESULTS += "java.sql.SQLException sE: " + sE.toString();

		} catch (ConnectionNotAvailableException cE) {

			RESULTS += "ConnectionNotAvailableException cE :" + cE.toString();
		} finally {

			if (conn != null) {
				cManager.releaseConnection(conn);
			}
		}

		return RowCount;
	}

	public String getInstructorActivityReport(String CourseId, String Sede) {

		ConnectionManager cManager = null;
		Connection conn = null;
		PreparedStatement selectQuery = null;

		StringBuilder Data = new StringBuilder();
		Data.append("<table>");
		Data.append("<tr>");
		Data.append("<td>");
		Data.append("DOCENTE");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("M&Oacute;DULO");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("&Uacute;LTIMO INGRESO");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("TIEMPO DEDICADO");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("NUM.POST");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("&Uacute;LTIMO POST");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("VENCIMIENTO");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("PARTICIPANTES");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("ENTREGAS");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("FALTA CALIF.");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("% SIN CALIF.");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("VENCIMIENTO");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("PARTICIPANTES");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("ENTREGAS");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("FALTA CALIF.");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("% SIN CALIF.");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("VENCIMIENTO");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("PARTICIPANTES");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("ENTREGAS");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("FALTA CALIF.");
		Data.append("</td>");
		Data.append("<td>");
		Data.append("% SIN CALIF.");
		Data.append("</td>");
		Data.append("</tr>");

		String RESULTS = "";

		try {

			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			// GET ALL
			// COURSES/INSTRUCTORS/DAYS_SINCE_LAST_ACCESS/TOTAL_TIME_SPENT_IN_COURSE/TOTAL_POSTS/DAYS_SINCE_LAST_POST

			StringBuilder TableData = new StringBuilder();
			TableData.append("SELECT INFO2.*,");
			TableData.append("CASE");
			TableData.append("	WHEN INFO2.NUM_POSTS = 0 THEN 'NUNCA'");
			TableData.append("	ELSE");
			TableData.append("	(");
			TableData.append("		CONCAT");
			TableData.append("		(	");
			TableData.append("			(TRUNC(SYSDATE) - TRUNC((");
			TableData.append(
					"									SELECT MAX(MSG.LAST_EDIT_DATE) FROM COURSE_MAIN COURSE");
			TableData.append(
					"									INNER JOIN CONFERENCE_MAIN CONF ON COURSE.PK1 = CONF.CRSMAIN_PK1");
			TableData.append(
					"									INNER JOIN FORUM_MAIN FORUM ON FORUM.CONFMAIN_PK1 = CONF.PK1");
			TableData.append(
					"									INNER JOIN MSG_MAIN MSG ON MSG.FORUMMAIN_PK1 = FORUM.PK1");
			TableData.append("									INNER JOIN USERS USERS ON USERS.PK1 = MSG.USERS_PK1");
			TableData.append("									WHERE COURSE.PK1 = INFO2.CM_PK1");
			TableData.append("									AND MSG.USERS_PK1 = INFO2.US_PK1");
			TableData.append("								))");
			TableData.append("			),' días'");
			TableData.append("		)");
			TableData.append("	)");
			TableData.append("END \"LAST_POST\",");
			TableData.append(
					" (SELECT COUNT(*) FROM COURSE_USERS CUU WHERE CUU.ROLE = 'S' AND CUU.CRSMAIN_PK1 = INFO2.CM_PK1) \"NUM_STUDENTS\"");
			TableData.append(" FROM(");
			TableData.append("	SELECT INFO.*,");
			TableData.append("	CASE");
			TableData.append("		WHEN INFO.LAST_ACCESS = 'NUNCA' THEN 0");
			TableData.append(
					"		ELSE (SELECT SUM(COURSE_ACCESS_MINUTES) FROM ODS_AA_COURSE_ACTIVITY WHERE USER_PK1 = INFO.US_PK1 AND COURSE_PK1 = INFO.CM_PK1)");
			TableData.append("	END \"TIME\",");
			TableData.append("	CASE");
			TableData.append("		WHEN INFO.LAST_ACCESS = 'NUNCA' THEN 0");
			TableData.append("		ELSE( ");
			TableData.append("			SELECT COUNT(*) FROM COURSE_MAIN COURSE");
			TableData.append("			INNER JOIN CONFERENCE_MAIN CONF ON COURSE.PK1 = CONF.CRSMAIN_PK1");
			TableData.append("			INNER JOIN FORUM_MAIN FORUM ON FORUM.CONFMAIN_PK1 = CONF.PK1");
			TableData.append("			INNER JOIN MSG_MAIN MSG ON MSG.FORUMMAIN_PK1 = FORUM.PK1");
			TableData.append("			INNER JOIN USERS USERS ON USERS.PK1 = MSG.USERS_PK1");
			TableData.append("			WHERE COURSE.PK1 = INFO.CM_PK1");
			TableData.append("			AND MSG.USERS_PK1 = INFO.US_PK1");
			TableData.append("		)");
			TableData.append("	END \"NUM_POSTS\"");
			TableData.append("	FROM(");
			TableData.append(
					"		SELECT CM.PK1 \"CM_PK1\",US.PK1 \"US_PK1\", US.FIRSTNAME, US.LASTNAME,CM.START_DATE, CM.COURSE_ID, CS.SEDE, ");
			TableData.append("		CASE");
			TableData.append(
					"		WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'NUNCA'");
			TableData.append("		ELSE");
			TableData.append("		CONCAT((TRUNC(SYSDATE)-TRUNC(CU.LAST_ACCESS_DATE)),' días')");
			TableData.append("		END \"LAST_ACCESS\"");
			TableData.append("		FROM COURSE_MAIN CM");
			TableData.append("		INNER JOIN COURSE_USERS CU ON CU.CRSMAIN_PK1 = CM.PK1");
			TableData.append("		INNER JOIN USERS US ON US.PK1 = CU.USERS_PK1");
			TableData.append("		INNER JOIN INSTITUTION_ROLES IR ON US.INSTITUTION_ROLES_PK1 = IR.PK1");
			if(Sede.equals("Todo")) {
				TableData.append(" INNER JOIN (");
				TableData.append(" select sede.name as sede, course_id");
				TableData.append(" from domain_course_coll_uid dom_crs inner join course_main course on course.pk1 = dom_crs.course_main_pk1 ");
				TableData.append(" INNER JOIN domain on domain.pk1 = dom_crs.domain_pk1 ");
				TableData.append(" inner join mi_node modulo  on modulo.domain_pk1 = domain.pk1 ");
				TableData.append(" inner join mi_node semestre on semestre.pk1 = modulo.parent_pk1 ");
				TableData.append(" inner join mi_node anio on anio.pk1 = semestre.parent_pk1 ");
				TableData.append(" inner join mi_node carrera on carrera.pk1 = anio.parent_pk1 ");
				TableData.append(" inner join mi_node escuela on escuela.pk1 = carrera.parent_pk1 ");
				TableData.append(" inner join mi_node sede on sede.pk1 = escuela.parent_pk1 ");
				TableData.append(" ) CS ON CS.COURSE_ID = CM.COURSE_ID ");
			} else {
				TableData.append(" INNER JOIN (");
				TableData.append(" select sede.name as sede, course_id");
				TableData.append(" from domain_course_coll_uid dom_crs inner join course_main course on course.pk1 = dom_crs.course_main_pk1 ");
				TableData.append(" INNER JOIN domain on domain.pk1 = dom_crs.domain_pk1 ");
				TableData.append(" inner join mi_node modulo  on modulo.domain_pk1 = domain.pk1 ");
				TableData.append(" inner join mi_node semestre on semestre.pk1 = modulo.parent_pk1 ");
				TableData.append(" inner join mi_node anio on anio.pk1 = semestre.parent_pk1 ");
				TableData.append(" inner join mi_node carrera on carrera.pk1 = anio.parent_pk1 ");
				TableData.append(" inner join mi_node escuela on escuela.pk1 = carrera.parent_pk1 ");
				TableData.append(" inner join mi_node sede on sede.pk1 = escuela.parent_pk1 ");
				TableData.append(" WHERE sede.name = '"+Sede+"') CS ON CS.COURSE_ID = CM.COURSE_ID ");
			}
			
			TableData.append("		WHERE CU.ROLE = 'Docente'");
			TableData.append("		AND CM.ROW_STATUS = 0 AND CU.ROW_STATUS = 0");
			if (CourseId.contains("%")) {

				TableData.append(" AND CM.COURSE_ID LIKE '"+CourseId+"' ");
			} else {
				TableData.append(" AND CM.COURSE_ID = '"+CourseId+"' ");
			}
			TableData.append("		AND CM.COURSE_ID NOT LIKE 'BASE%'");
			TableData.append("		ORDER BY CM.PK1 DESC");
			TableData.append("	) \"INFO\"");
			TableData.append(") \"INFO2\"");
			
			System.out.println("Control Docentes Reporte = " + TableData.toString());
			
			selectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			ResultSet rSet = selectQuery.executeQuery();

			RESULTS = "<table border=1>";
			RESULTS += "<thead>";
			RESULTS += "<tr>";

			String ColumnName = "";

			ColumnName = "DOCENTE";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "M&Oacute;DULO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";
			
			ColumnName = "FECHA DE INICIO";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";
			
			ColumnName = "Sede";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "&Uacute;LT. ING.";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "TMPO. DED.";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "NUM. POST";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "&Uacute;LT. POST";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + ColumnName + "</th>";

			ColumnName = "UNIDAD 1";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + "<table width='100%'>" + "<tbody>" + "<tr>"
					+ "<td colspan='5'><center><b>" + ColumnName + "</b></center></td>" + "</tr>" + "<tr>"
					+ "<td align='center' width='20%'>VEN</td>" + "<td align='center' width='20%'>PT</td>"
					+ "<td align='center' width='20%'>EN</td>" + "<td align='center' width='20%'>FC</td>"
					+ "<td align='center' width='20%'>%</td>" + "</tr>" + "</tbody>" + "</table>" + "</th>";

			ColumnName = "UNIDAD 2";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + "<table width='100%'>" + "<tbody>" + "<tr>"
					+ "<td colspan='5'><center><b>" + ColumnName + "</b></center></td>" + "</tr>" + "<tr>"
					+ "<td align='center' width='20%'>VEN</td>" + "<td align='center' width='20%'>PT</td>"
					+ "<td align='center' width='20%'>EN</td>" + "<td align='center' width='20%'>FC</td>"
					+ "<td align='center' width='20%'>%</td>" + "</tr>" + "</tbody>" + "</table>" + "</th>";

			ColumnName = "UNIDAD 3";
			RESULTS += "<th data-column-id='" + ColumnName + "'>" + "<table width='100%'>" + "<tbody>" + "<tr>"
					+ "<td colspan='5'><center><b>" + ColumnName + "</b></center></td>" + "</tr>" + "<tr>"
					+ "<td align='center' width='20%'>VEN</td>" + "<td align='center' width='20%'>PT</td>"
					+ "<td align='center' width='20%'>EN</td>" + "<td align='center' width='20%'>FC</td>"
					+ "<td align='center' width='20%'>%</td>" + "</tr>" + "</tbody>" + "</table>" + "</th>";

			RESULTS += "</tr>";
			RESULTS += "</thead>";
			RESULTS += "<tbody>";

			ArrayList<String> CoursePk1s = new ArrayList<String>();
			ArrayList<String> InstructorsPk1s = new ArrayList<String>();

			String CoursePk1 = "";
			String InstructorsPk1 = "";
			String Name = "";
			String CourseName = "";
			String Last_Access = "";
			String TimeSpent = "";
			String NumPosts = "";
			String Last_Post = "";
			String Sede_Curso = "";
			String End_Date = "";
			String Num_Stu = "";
			String Num_Assign = "";
			String Not_Graded = "";
			String Percent = "";
			String StartDate = "";

			String Last_AccessColor = "style=\"background-color: indianred;\"";
			String PercentColor = "";

			long count = 0L;

			int NumAreas = -1;
			String AssignId = "NOT FOUND";

			ResultSet TemprSet = null;
			PreparedStatement TempselectQuery = null;

			ArrayList<String> Course_Contents_Pk1 = null;

			while (rSet.next()) {
				
				CoursePk1 = rSet.getString("CM_PK1");
				InstructorsPk1 = rSet.getString("US_PK1");
				Name = rSet.getString("FIRSTNAME") + " " + rSet.getString("LASTNAME");
				CourseName = rSet.getString("COURSE_ID");
				Last_Access = rSet.getString("LAST_ACCESS");
				TimeSpent = rSet.getString("TIME");
				NumPosts = rSet.getString("NUM_POSTS");
				Last_Post = rSet.getString("LAST_POST");
				Num_Stu = rSet.getString("NUM_STUDENTS");
				Sede_Curso = rSet.getString("SEDE");
				StartDate = rSet.getString("START_DATE");
				
				if(TimeSpent == null){
					
					TimeSpent = "0";
				}

				if(Last_Access == null){
					
					Last_Access = "NUNCA";
				}

				if (!TimeSpent.equals("0")) {

					TimeSpent = parseTime(TimeSpent);
				} else {

					TimeSpent += " segs";
				}

				if (Last_Access.equals("NUNCA")) {

					Last_AccessColor = "style=\"background-color: indianred;\"";
				} else {
					Last_AccessColor = "";

					if (Integer.parseInt(Last_Access.split(" ")[0]) > 7) {

						Last_AccessColor = "style=\"background-color: indianred;\"";
					}
				}

				RESULTS += "<tr>";
				RESULTS += "<td>" + Name + "</td>";
				RESULTS += "<td><a target='_blank' href='/webapps/blackboard/execute/courseMain?course_id="
						+ rSet.getString(1) + "'>" + CourseName + "</a></td>";
				RESULTS += "<td>" + StartDate.split(" ")[0] + "</td>";
				RESULTS += "<td>" + Sede_Curso + "</td>";
				RESULTS += "<td " + Last_AccessColor + ">" + Last_Access + "</td>";
				RESULTS += "<td>" + TimeSpent + "</td>";
				RESULTS += "<td>" + NumPosts + "</td>";
				RESULTS += "<td>" + Last_Post + "</td>";

				Data.append("<tr>");
				Data.append("<td>" + Name + "</td>");
				Data.append("<td>" + CourseName + "</td>");
				Data.append("<td>" + Last_Access + "</td>");
				Data.append("<td>" + TimeSpent + "</td>");
				Data.append("<td>" + NumPosts + "</td>");
				Data.append("<td>" + Last_Post + "</td>");

				// GET COURSE AREAS
				TableData = new StringBuilder();
				TableData.append("SELECT * FROM( ");
				TableData.append(
						"	SELECT CT.COURSE_CONTENTS_PK1, CT.LABEL, CT.POSITION, COUNT(*) FROM COURSE_MAIN CM ");
				TableData.append("	INNER JOIN COURSE_TOC CT ON CT.CRSMAIN_PK1 = CM.PK1 ");
				TableData.append("	INNER JOIN COURSE_CONTENTS CC ON CC.PARENT_PK1 = CT.COURSE_CONTENTS_PK1 ");
				TableData.append("	WHERE CM.PK1 = " + CoursePk1 + " ");
				TableData.append("	AND CT.TARGET_TYPE = 'CONTENT_LINK' ");
				TableData.append(
						"	AND CC.CNTHNDLR_HANDLE IN ('resource/x-bb-forumlink','resource/x-bb-assignment','resource/x-bb-asmt-test-link') ");
				TableData.append("	GROUP BY CT.COURSE_CONTENTS_PK1, CT.LABEL, CT.POSITION ");
				TableData.append(") CTT ORDER BY CTT.POSITION ASC ");

				TempselectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
						ResultSet.CONCUR_READ_ONLY);

				TemprSet = TempselectQuery.executeQuery();

				Course_Contents_Pk1 = new ArrayList<String>();

				while (TemprSet.next()) {

					Course_Contents_Pk1.add(TemprSet.getString(1));
				}

				TemprSet.close();

				TempselectQuery.close();

				NumAreas = Course_Contents_Pk1.size();

				if (NumAreas > 3) {

					NumAreas = 3;
				}

				for (int i = 0; i < NumAreas; i++) {

					AssignId = "NOT FOUND";

					// GET ASSIGNMENT FROM SPECIFIC COURSE AREA
					TableData = new StringBuilder();
					TableData.append("SELECT CC.PK1 FROM COURSE_MAIN CM ");
					TableData.append("INNER JOIN COURSE_CONTENTS CC ON CC.CRSMAIN_PK1 = CM.PK1 ");
					TableData.append("WHERE CM.PK1 = " + CoursePk1 + " ");
					TableData.append("AND CC.CNTHNDLR_HANDLE = 'resource/x-bb-assignment' ");
					TableData.append("AND ISNULL(CC.PARENT_PK1,-1) != -1 ");
					TableData.append("AND CC.PARENT_PK1 IN (" + Course_Contents_Pk1.get(i) + ", ");
					TableData.append("(SELECT CCS.PK1 FROM COURSE_CONTENTS CCS ");
					TableData.append("WHERE CCS.PARENT_PK1 = " + Course_Contents_Pk1.get(i) + " ");
					TableData.append("AND CCS.CNTHNDLR_HANDLE = 'resource/x-bb-folder') ");
					TableData.append(") ");
					TableData.append("AND ROWNUM = 1 ");
					TableData.append("ORDER BY CC.PK1 ");

					TempselectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
							ResultSet.CONCUR_READ_ONLY);

					TemprSet = TempselectQuery.executeQuery();

					while (TemprSet.next()) {

						AssignId = TemprSet.getString(1);

						if (AssignId == null) {

							AssignId = "NOT FOUND";
						}
					}

					TemprSet.close();

					TempselectQuery.close();

					if (!AssignId.equals("NOT FOUND")) {

						TableData = new StringBuilder();
						TableData.append("SELECT ( ");
						TableData.append("	SELECT ");
						TableData.append("		CASE");
						TableData.append(
								"			WHEN NVL(GM.DUE_DATE,TO_DATE('0001/01/01', 'YYYY-MM-DD')) = TO_DATE('0001/01/01', 'YYYY-MM-DD') THEN 'N/A'");
						TableData.append("			ELSE CONCAT(TRUNC(GM.DUE_DATE),' ')");
						TableData.append("		END \"END_DATE\"");
						TableData.append("		FROM COURSE_MAIN CM");
						TableData.append("		INNER JOIN GRADEBOOK_MAIN GM ON GM.CRSMAIN_PK1 = CM.PK1");
						TableData.append("		WHERE CM.PK1 = " + CoursePk1 + " ");
						TableData.append("		AND GM.COURSE_CONTENTS_PK1 = " + AssignId + "  ");
						TableData.append(") \"END_DATE\",INFO2.*,");
						TableData.append("	CASE");
						TableData.append("		WHEN INFO2.SUBMISSION = 0 THEN '0%'");
						TableData.append("		ELSE (CONCAT(TRUNC((INFO2.NOT_GRADED/INFO2.SUBMISSION)*100),'%'))");
						TableData.append("	END \"PERCENTAGE\"");
						TableData.append(" FROM(");
						TableData.append("	SELECT INFO.*,");
						TableData.append("		CASE");
						TableData.append("			WHEN INFO.SUBMISSION = 0 THEN 0");
						TableData.append("			ELSE (");
						TableData.append(
								"				SELECT COUNT(DISTINCT GG.COURSE_USERS_PK1) FROM GRADEBOOK_MAIN GM");
						TableData.append("				INNER JOIN COURSE_MAIN CM ON CM.PK1 = GM.CRSMAIN_PK1");
						TableData.append(
								"				INNER JOIN GRADEBOOK_GRADE GG ON GG.GRADEBOOK_MAIN_PK1 = GM.PK1");
						TableData.append("				INNER JOIN ATTEMPT AT ON AT.GRADEBOOK_GRADE_PK1 = GG.PK1");
						TableData.append("				WHERE CM.PK1 = 	" + CoursePk1 + " ");
						TableData.append("				AND NVL(AT.FIRST_GRADED_DATE,'01/JAN/0001') = '01/JAN/0001'");
						TableData.append("				AND AT.STATUS != 3");
						TableData.append("				AND GM.COURSE_CONTENTS_PK1 = " + AssignId + " ");
						TableData.append("			)");
						TableData.append("		END \"NOT_GRADED\"");
						TableData.append("	 FROM(");
						TableData.append(
								"		SELECT COUNT(DISTINCT GG.COURSE_USERS_PK1) \"SUBMISSION\" FROM GRADEBOOK_MAIN GM");
						TableData.append("		INNER JOIN COURSE_MAIN CM ON CM.PK1 = GM.CRSMAIN_PK1");
						TableData.append("		INNER JOIN GRADEBOOK_GRADE GG ON GG.GRADEBOOK_MAIN_PK1 = GM.PK1");
						TableData.append("		INNER JOIN ATTEMPT AT ON AT.GRADEBOOK_GRADE_PK1 = GG.PK1");
						TableData.append("		WHERE CM.PK1 = 	" + CoursePk1 + " ");
						TableData.append("		AND GM.COURSE_CONTENTS_PK1 = " + AssignId + " ");
						TableData.append("	) \"INFO\" ");
						TableData.append(") \"INFO2\"");

						TempselectQuery = conn.prepareStatement(TableData.toString(), ResultSet.TYPE_FORWARD_ONLY,
								ResultSet.CONCUR_READ_ONLY);

						TemprSet = TempselectQuery.executeQuery();

						while (TemprSet.next()) {

							End_Date = TemprSet.getString(1);
							Num_Assign = TemprSet.getString(2);
							Not_Graded = TemprSet.getString(3);
							Percent = TemprSet.getString(4);

							if (End_Date == null) {

								End_Date = "N/A";
							} else {
								if (!End_Date.equals("N/A")) {

									End_Date = End_Date.split(" ")[0];
								}
							}
						}

						TemprSet.close();

						TempselectQuery.close();

						PercentColor = "";

						if (Integer.parseInt(Percent.replace("%", "")) > 69) {

							PercentColor = ";background-color: indianred;";
						}

						RESULTS += "<td>" + "<table width='100%' cellspacing='1' cellpadding='1'>" + "<tbody>"
								+ "<tr class='celdaColumna'>"
								+ "<td align='center' class='tablaHistDatos' style='width:21%'>" + End_Date + "</td>"
								+ "<td align='center' class='tablaHistDatos' style='width:20%'>" + Num_Stu + "</td>"
								+ "<td align='center' class='tablaHistDatos' style='width:20%'>" + Num_Assign + "</td>"
								+ "<td align='center' class='tablaHistDatos' style='width:20%'>" + Not_Graded + "</td>"
								+ "<td align='center' class='tablaHistDatos' style='width:20%" + PercentColor + "'>"
								+ Percent + "</td>" + "</tr>" + "</tbody>" + "</table>" + "</td>";

						Data.append("<td>" + End_Date + "</td>");
						Data.append("<td>" + Num_Stu + "</td>");
						Data.append("<td>" + Num_Assign + "</td>");
						Data.append("<td>" + Not_Graded + "</td>");
						Data.append("<td>" + Percent + "</td>");
					} else {

						RESULTS += "<td>" + "<table width='100%' cellspacing='1' cellpadding='1'>" + "<tbody>"
								+ "<tr class='celdaColumna'>"
								+ "<td colspan='5' align='center' class='tablaHistDatos' style='width:100%'>Sin Tarea Sumativa</td>"
								+ "</tr>" + "</tbody>" + "</table>" + "</td>";

						Data.append("<td>-</td>");
						Data.append("<td>-</td>");
						Data.append("<td>-</td>");
						Data.append("<td>-</td>");
						Data.append("<td>-</td>");
					}
				}

				for (int i = 0; i < (3 - NumAreas); i++) {

					RESULTS += "<td>" + "<table width='100%' cellspacing='1' cellpadding='1'>" + "<tbody>"
							+ "<tr class='celdaColumna'>"
							+ "<td align='center' class='tablaHistDatos' style='width:100%'>Sin Tarea Sumativa</td>"
							+ "</tr>" + "</tbody>" + "</table>" + "</td>";

					Data.append("<td>-</td>");
					Data.append("<td>-</td>");
					Data.append("<td>-</td>");
					Data.append("<td>-</td>");
					Data.append("<td>-</td>");
				}

				RESULTS += "</tr>";
				Data.append("</tr>");

				count++;
			}

			rSet.close();

			selectQuery.close();

			RESULTS += "</tbody>";
			RESULTS += "</table>";

			Data.append("</table>");
			//RESULTS = Data.toString();
			//RESULTS += "<div id='dvData' style='display:none;'>" + Data.toString() + "</div>";

		} catch (java.sql.SQLException sE) {

			RESULTS += "java.sql.SQLException sE: " + sE.toString();

		} catch (ConnectionNotAvailableException cE) {

			RESULTS += "ConnectionNotAvailableException cE :" + cE.toString();
		} finally {

			if (conn != null) {
				cManager.releaseConnection(conn);
			}
		}

		return RESULTS;
	}

	public int getStudentControlPagesNum(String Headquarter , String modalidad) {
		StringBuilder RESULTS = new StringBuilder();
		ConnectionManager cManager = null;
		Connection conn = null;

		cManager = BbDatabase.getDefaultInstance().getConnectionManager();
		int numberOfPages = 1;
		try {
			conn = cManager.getConnection();
			
			StringBuilder tableDataCount =  new StringBuilder();
			tableDataCount.append("SELECT COUNT(*) FROM (");
			tableDataCount.append("SELECT US.PK1, US.LASTNAME, US.FIRSTNAME, EXTRACT(year from US.DTCREATED) \"COHORTE\","
					+ " US.EMAIL, US.LAST_LOGIN_DATE, (systimestamp - US.LAST_LOGIN_DATE) \"Date Diff\" "
					+ "FROM USERS US ");

			if (Headquarter.equals("Todo") && modalidad.equals("Todo")) {
				tableDataCount.append("WHERE US.INSTITUTION_ROLES_PK1 IN ( ");
				tableDataCount.append("SELECT PK1 FROM INSTITUTION_ROLES ");
				tableDataCount.append("WHERE ROLE_ID IN('Online','Semipresencial','Presencial')) ");
				
			} else if (Headquarter.equals("Todo")) {
				tableDataCount.append("WHERE US.INSTITUTION_ROLES_PK1=" + modalidad + " ");
			} else if (modalidad.equals("Todo")) {
				tableDataCount.append("WHERE US.INSTITUTION_ROLES_PK1 IN ( ");
				tableDataCount.append("SELECT PK1 FROM INSTITUTION_ROLES ");
				tableDataCount.append("WHERE ROLE_ID IN('Online','Semipresencial','Presencial')) ");
				tableDataCount.append("AND US.B_PHONE_1 ='" + Headquarter + "' ");
				tableDataCount.append("AND US.B_PHONE_1 IS NOT NULL ");
			} else {
				tableDataCount.append("WHERE US.INSTITUTION_ROLES_PK1=" + modalidad + " ");
				tableDataCount.append("AND US.B_PHONE_1= '" + Headquarter + "' ");
				tableDataCount.append("AND US.B_PHONE_1 IS NOT NULL ");
			}
			tableDataCount.append("AND US.SYSTEM_ROLE = 'N' ");
			tableDataCount.append("AND US.DATA_SRC_PK1 != 2 )");
			
			
			ResultSet rs = conn.createStatement().executeQuery(tableDataCount.toString());

			if (rs.next()) {
				int numberOfRows = rs.getInt(1);
				numberOfPages = (int) Math.ceil((double)numberOfRows / this.pageSize);
				
			}
		} catch (ConnectionNotAvailableException e) {
			e.printStackTrace();
			RESULTS.append(e.getFullMessageTrace());
		} catch (SQLException e) {
			e.printStackTrace();
			RESULTS.append(e.getMessage());
		}
		cManager.releaseConnection(conn);
		return numberOfPages;
	}
	
	
	public String getTablePagesCourseControl(String Headquarter, int currentPage, String modalidad) {
		StringBuilder RESULTS = new StringBuilder();
		ConnectionManager cManager = null;
		Connection conn = null;

		cManager = BbDatabase.getDefaultInstance().getConnectionManager();

		try {
			conn = cManager.getConnection();

			StringBuilder TableData = new StringBuilder();
			TableData.append("SELECT COUNT(*) ");
			TableData.append("FROM COURSE_MAIN CM ");
			TableData.append("INNER JOIN COURSE_USERS CU ON CU.CRSMAIN_PK1 = CM.PK1 ");
			TableData.append("INNER JOIN USERS US ON US.PK1 = CU.USERS_PK1 ");
			TableData.append("INNER JOIN INSTITUTION_ROLES IR ON US.INSTITUTION_ROLES_PK1 = IR.PK1 ");
			TableData.append("WHERE CU.ROLE = 'Docente' ");
			TableData.append(
					"AND NVL(CM.END_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) != to_date('0001/01/01', 'yyyy-mm-dd') ");
			TableData.append("AND CM.ROW_STATUS = 0 AND CU.ROW_STATUS = 0");
			TableData.append("ORDER BY CM.COURSE_NAME ");
			
			System.out.println("Course Control Query");
			System.out.println(TableData.toString());
			
			ResultSet rs = conn.createStatement().executeQuery(TableData.toString());

			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
				double paginas = (double) (count) / pageSize;
				int i = 1;
				while (paginas > 0) {
					if (i == currentPage) {
						RESULTS.append("<li style=\"display:inline-block;\" " + "class=\"active\"><a href=\"#\">" + i
								+ "</a></li>");
					} else {
						RESULTS.append("<li style=\"display:inline-block;\"><a href=\"?page="+i+"\">" + i + "</a></li>");
					}
					paginas--;
					i++;
				}
			} else
				return "";

		} catch (ConnectionNotAvailableException e) {
			e.printStackTrace();
			RESULTS.append(e.getFullMessageTrace());
		} catch (SQLException e) {
			e.printStackTrace();
			RESULTS.append(e.getMessage());
		}

		cManager.releaseConnection(conn);
		return RESULTS.toString();
	}
	
public String getTablePagesInstructorActivity(String CourseId, String Sede) {
		StringBuilder RESULTS = new StringBuilder();
		ConnectionManager cManager = null;
		Connection conn = null;

		cManager = BbDatabase.getDefaultInstance().getConnectionManager();
		try {
			conn = cManager.getConnection();

			StringBuilder TableData = new StringBuilder();
			TableData.append("SELECT COUNT(*) FROM (SELECT INFO2.*,");
			TableData.append("CASE");
			TableData.append("	WHEN INFO2.NUM_POSTS = 0 THEN 'NUNCA'");
			TableData.append("	ELSE");
			TableData.append("	(");
			TableData.append("		CONCAT");
			TableData.append("		(	");
			TableData.append("			(TRUNC(SYSDATE) - TRUNC((");
			TableData.append(
					"									SELECT MAX(MSG.LAST_EDIT_DATE) FROM COURSE_MAIN COURSE");
			TableData.append(
					"									INNER JOIN CONFERENCE_MAIN CONF ON COURSE.PK1 = CONF.CRSMAIN_PK1");
			TableData.append(
					"									INNER JOIN FORUM_MAIN FORUM ON FORUM.CONFMAIN_PK1 = CONF.PK1");
			TableData.append(
					"									INNER JOIN MSG_MAIN MSG ON MSG.FORUMMAIN_PK1 = FORUM.PK1");
			TableData.append("									INNER JOIN USERS USERS ON USERS.PK1 = MSG.USERS_PK1");
			TableData.append("									WHERE COURSE.PK1 = INFO2.CM_PK1");
			TableData.append("									AND MSG.USERS_PK1 = INFO2.US_PK1");
			TableData.append("								))");
			TableData.append("			),' días'");
			TableData.append("		)");
			TableData.append("	)");
			TableData.append("END \"LAST_POST\",");
			TableData.append(
					" (SELECT COUNT(*) FROM COURSE_USERS CUU WHERE CUU.ROLE = 'S' AND CUU.CRSMAIN_PK1 = INFO2.CM_PK1) \"NUM_STUDENTS\"");
			TableData.append(" FROM(");
			TableData.append("	SELECT INFO.*,");
			TableData.append("	CASE");
			TableData.append("		WHEN INFO.LAST_ACCESS = 'NUNCA' THEN 0");
			TableData.append(
					"		ELSE (SELECT SUM(COURSE_ACCESS_MINUTES) FROM ODS_AA_COURSE_ACTIVITY WHERE USER_PK1 = INFO.US_PK1 AND COURSE_PK1 = INFO.CM_PK1)");
			TableData.append("	END \"TIME\",");
			TableData.append("	CASE");
			TableData.append("		WHEN INFO.LAST_ACCESS = 'NUNCA' THEN 0");
			TableData.append("		ELSE( ");
			TableData.append("			SELECT COUNT(*) FROM COURSE_MAIN COURSE");
			TableData.append("			INNER JOIN CONFERENCE_MAIN CONF ON COURSE.PK1 = CONF.CRSMAIN_PK1");
			TableData.append("			INNER JOIN FORUM_MAIN FORUM ON FORUM.CONFMAIN_PK1 = CONF.PK1");
			TableData.append("			INNER JOIN MSG_MAIN MSG ON MSG.FORUMMAIN_PK1 = FORUM.PK1");
			TableData.append("			INNER JOIN USERS USERS ON USERS.PK1 = MSG.USERS_PK1");
			TableData.append("			WHERE COURSE.PK1 = INFO.CM_PK1");
			TableData.append("			AND MSG.USERS_PK1 = INFO.US_PK1");
			TableData.append("		)");
			TableData.append("	END \"NUM_POSTS\"");
			TableData.append("	FROM(");
			TableData.append(
					"		SELECT CM.PK1 \"CM_PK1\",US.PK1 \"US_PK1\", US.FIRSTNAME, US.LASTNAME, CM.COURSE_ID, CS.SEDE, ");
			TableData.append("		CASE");
			TableData.append(
					"		WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'NUNCA'");
			TableData.append("		ELSE");
			TableData.append("		CONCAT((TRUNC(SYSDATE)-TRUNC(CU.LAST_ACCESS_DATE)),' días')");
			TableData.append("		END \"LAST_ACCESS\"");
			TableData.append("		FROM COURSE_MAIN CM");
			TableData.append("		INNER JOIN COURSE_USERS CU ON CU.CRSMAIN_PK1 = CM.PK1");
			TableData.append("		INNER JOIN USERS US ON US.PK1 = CU.USERS_PK1");
			TableData.append("		INNER JOIN INSTITUTION_ROLES IR ON US.INSTITUTION_ROLES_PK1 = IR.PK1");
			if(Sede.equals("Todo")) {
				TableData.append(" INNER JOIN (");
				TableData.append(" select sede.name as sede, course_id");
				TableData.append(" from domain_course_coll_uid dom_crs inner join course_main course on course.pk1 = dom_crs.course_main_pk1 ");
				TableData.append(" INNER JOIN domain on domain.pk1 = dom_crs.domain_pk1 ");
				TableData.append(" inner join mi_node modulo  on modulo.domain_pk1 = domain.pk1 ");
				TableData.append(" inner join mi_node semestre on semestre.pk1 = modulo.parent_pk1 ");
				TableData.append(" inner join mi_node anio on anio.pk1 = semestre.parent_pk1 ");
				TableData.append(" inner join mi_node carrera on carrera.pk1 = anio.parent_pk1 ");
				TableData.append(" inner join mi_node escuela on escuela.pk1 = carrera.parent_pk1 ");
				TableData.append(" inner join mi_node sede on sede.pk1 = escuela.parent_pk1 ");
				TableData.append(" ) CS ON CS.COURSE_ID = CM.COURSE_ID ");
			} else {
				TableData.append(" INNER JOIN (");
				TableData.append(" select sede.name as sede, course_id");
				TableData.append(" from domain_course_coll_uid dom_crs inner join course_main course on course.pk1 = dom_crs.course_main_pk1 ");
				TableData.append(" INNER JOIN domain on domain.pk1 = dom_crs.domain_pk1 ");
				TableData.append(" inner join mi_node modulo  on modulo.domain_pk1 = domain.pk1 ");
				TableData.append(" inner join mi_node semestre on semestre.pk1 = modulo.parent_pk1 ");
				TableData.append(" inner join mi_node anio on anio.pk1 = semestre.parent_pk1 ");
				TableData.append(" inner join mi_node carrera on carrera.pk1 = anio.parent_pk1 ");
				TableData.append(" inner join mi_node escuela on escuela.pk1 = carrera.parent_pk1 ");
				TableData.append(" inner join mi_node sede on sede.pk1 = escuela.parent_pk1 ");
				TableData.append(" WHERE sede.name = '"+Sede+"') CS ON CS.COURSE_ID = CM.COURSE_ID ");
			}
			
			TableData.append("		WHERE CU.ROLE = 'Docente'");
			TableData.append("		AND CM.ROW_STATUS = 0 AND CU.ROW_STATUS = 0");
			if (CourseId.contains("%")) {

				TableData.append(" AND CM.COURSE_ID LIKE '"+CourseId+"' ");
			} else {
				TableData.append(" AND CM.COURSE_ID = '"+CourseId+"' ");
			}
			TableData.append("		AND CM.COURSE_ID NOT LIKE 'BASE%'");
			TableData.append("		ORDER BY CM.PK1 DESC");
			TableData.append("	) \"INFO\"");
			TableData.append(") \"INFO2\")");
			
			ResultSet rs = conn.createStatement().executeQuery(TableData.toString());

			if (rs.next()) {
				double items = rs.getDouble(1) / Double.valueOf(this.pageSize);
				items = Math.ceil(items);
				return "<label> de <label id='TotalPages'>"+ String.valueOf((int)items) + "</label> paginas.</label> ";
			} else
				return "1";

		} catch (ConnectionNotAvailableException e) {
			e.printStackTrace();
			RESULTS.append(e.getFullMessageTrace());
		} catch (SQLException e) {
			e.printStackTrace();
			RESULTS.append(e.getMessage());
		}

		cManager.releaseConnection(conn);
		return RESULTS.toString();
	}
	
	public String getTablePagesInstructorAccess(String Headquarter, int currentPage, String modalidad) {
		StringBuilder RESULTS = new StringBuilder();
		ConnectionManager cManager = null;
		Connection conn = null;

		cManager = BbDatabase.getDefaultInstance().getConnectionManager();

		try {
			conn = cManager.getConnection();

			StringBuilder TableData = new StringBuilder();
			TableData.append("SELECT COUNT(*) FROM ( ");
			TableData.append("SELECT ");
			TableData.append(
					" CM.PK1 \"COURSE_PK1\",US.PK1 \"USER_PK1\", CONCAT(CONCAT(CM.COURSE_ID,'-'), CM.COURSE_NAME) \"CURSO\",");
			TableData.append(
					" (SELECT COUNT(*) FROM COURSE_USERS CUU WHERE CUU.CRSMAIN_PK1 = CM.PK1 AND CUU.ROLE = 'S') \"ESTUDIANTES\",");
			TableData.append(" US.LASTNAME, US.FIRSTNAME, US.EMAIL,US.USER_ID,");
			TableData.append(" CASE");
			TableData.append(
					"     WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'NO'");
			TableData.append("     ELSE 'SI'");
			TableData.append(" END \"ACCEDIO AL CURSO\",");
			TableData.append(" CASE");
			TableData.append(
					"     WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 'NUNCA'");
			TableData.append("     ELSE");
			TableData.append("     CONCAT((TRUNC(SYSDATE)-TRUNC(CU.LAST_ACCESS_DATE)),' días')");
			TableData.append(" END \"LAST_ACCESS\",");
			TableData.append(" CASE");
			TableData.append(
					"     WHEN NVL(CU.LAST_ACCESS_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) = to_date('0001/01/01', 'yyyy-mm-dd') THEN 0");
			TableData.append(
					"     ELSE (SELECT SUM(COURSE_ACCESS_MINUTES) FROM ODS_AA_COURSE_ACTIVITY WHERE USER_PK1 = US.PK1 AND COURSE_PK1 = CM.PK1)");
			TableData.append(" END \"TIME\",");
			TableData.append(" CM.START_DATE \"FECHA INICIAL\",");
			TableData.append(" CM.START_DATE + 7 \"Semana 1\",");
			TableData.append(" CM.START_DATE + 14 \"Semana 2\",");
			TableData.append(" CM.START_DATE + 21 \"Semana 3\",");
			TableData.append(" CM.START_DATE + 28 \"Semana 4\",");
			TableData.append(" CM.START_DATE + 35 \"Semana 5\",");
			TableData.append(" CM.START_DATE + 42 \"Semana 6\",");
			TableData.append(" CM.START_DATE + 49 \"Semana 7\",");
			TableData.append(" CM.START_DATE + 56 \"Semana 8\",");
			TableData.append(" CM.START_DATE + 69 \"Semana 9\",");
			TableData.append(" TRUNC(CM.END_DATE) AS \"END_DATE\" ");
			TableData.append("FROM COURSE_MAIN CM ");
			TableData.append("INNER JOIN COURSE_USERS CU ON CU.CRSMAIN_PK1 = CM.PK1 ");
			TableData.append("INNER JOIN USERS US ON US.PK1 = CU.USERS_PK1 ");
			TableData.append("INNER JOIN INSTITUTION_ROLES IR ON US.INSTITUTION_ROLES_PK1 = IR.PK1 ");
			TableData.append("WHERE CU.ROLE IN ('Docente','P') ");
			TableData.append("AND CM.ROW_STATUS = 0 AND CU.ROW_STATUS = 0");
			
			if (!modalidad.equals("Todo")){
				
				TableData.append("AND CM.COURSE_ID LIKE '%-"+ modalidad +"'");
			}
			
			if (!Headquarter.equals("Todo")) {

				TableData.append("AND US.B_PHONE_1 = '" + Headquarter + "' ");
			}
			TableData.append(
					"AND NVL(CM.END_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) != to_date('0001/01/01', 'yyyy-mm-dd') ");
			TableData.append(
					"AND NVL(CM.START_DATE,to_date('0001/01/01', 'yyyy-mm-dd')) != to_date('0001/01/01', 'yyyy-mm-dd') ");
			TableData.append("ORDER BY CM.PK1 DESC ");
			TableData.append(")");
			
			System.out.println("Instructors Control Query");
			System.out.println(TableData.toString());
			
			ResultSet rs = conn.createStatement().executeQuery(TableData.toString());

			int count = 0;
			if (rs.next()) {
				count = rs.getInt(1);
				double paginas = (double) (count) / pageSize;
				int i = 1;
				while (paginas > 0) {
					if (i == currentPage) {
						RESULTS.append("<li style=\"display:inline-block;\" " + "class=\"active\"><a href=\"#\">" + i
								+ "</a></li>");
					} else {
						RESULTS.append("<li onclick=\"goToPage("+i+")\" style=\"display:inline-block;\"><a href=\"#\">"+i+"</a></li>");
					}
					paginas--;
					i++;
				}
			} else
				return "";

		} catch (ConnectionNotAvailableException e) {
			e.printStackTrace();
			RESULTS.append(e.getFullMessageTrace());
		} catch (SQLException e) {
			e.printStackTrace();
			RESULTS.append(e.getMessage());
		}

		cManager.releaseConnection(conn);
		return RESULTS.toString();
	}

	
	// -------------------------------------------------------------------------------------------------------------------------------

	@RequestMapping(value = { "MonitoringTool" }, method = RequestMethod.GET)
	public String getPageExecuteQuery(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

		try {

			Context ctx = ContextManagerFactory.getInstance().getContext();
			ContextManagerFactory.getInstance().setContext(request);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "MonitoringTool";
	}

	@RequestMapping(value = { "StudentControl" }, method = RequestMethod.GET)
	public String GetBBLearnQuery(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

		try {

			ContextManagerFactory.getInstance().setContext(request);

			String HTMLHeadquarters = this.getHeadquarters();
			String HTMLModalidades = getModalidades();
			String ReportName = "Control de Estudiantes";

			model.addAttribute("HTMLHeadquarters", HTMLHeadquarters);
			model.addAttribute("HTMLModalidades", HTMLModalidades);
			model.addAttribute("ReportName", ReportName);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return "StudentControl";

	}

	@RequestMapping(value = { "StudentsInfo" }, method = RequestMethod.GET)
	public String GetGetBBLearnQuery(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

		try {

			Context ctx = ContextManagerFactory.getInstance().getContext();
			ContextManagerFactory.getInstance().setContext(request);

			String Headquarter = request.getParameter("Sede");
			String Modalidad = request.getParameter("Modalidad");
			String pageString = request.getParameter("currentPage");
			String HTMLTable = "You Selected an invalid option";
			int currentPage = Integer.valueOf(pageString != null ? pageString : "1");
			int totalPages = this.getStudentControlPagesNum(Headquarter, Modalidad);

			if (Headquarter != null) {
				HTMLTable = getStudentControl(Headquarter, currentPage, Modalidad);

			}

			model.addAttribute("HTMLTable", HTMLTable);
			model.addAttribute("totalPages", totalPages);
			model.addAttribute("currentPage", currentPage);
			


		} catch (Exception e) {

			e.printStackTrace();
		}

		return "StudentsInfo";

	}

	@RequestMapping(value = { "SendEmail" }, method = RequestMethod.GET)
	public String GetSendEmail(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

		try {

			Context ctx = ContextManagerFactory.getInstance().getContext();
			ContextManagerFactory.getInstance().setContext(request);

			String Emails = request.getParameter("Emails");
			String Ids = request.getParameter("Ids");
			String Names = request.getParameter("Names");
			String Status = request.getParameter("Status");
			String Format = request.getParameter("Format");
			
			String FormatText = "";
			
			if (Format.equals("1")) {

				//PlainText = "Estimad@ [nombre_estudiante] \r\n \r\nJunto con saludarle y esperando que se encuentre bien, hemos detectado que desde que se iniciaron las clases no tiene conexión en la plataforma Semipresencial y nos interesa saber las razones de su ausencia. \r\nEs muy importante que pueda infórmanos cuál es el motivo de esta situación, de modo de poder asistirle y orientarlo sobre los pasos a seguir para regularizar su situación.\r\n\r\n\r\nAtenta a sus comentarios.\r\n\r\nCarolina Mardones I.\r\nCoordinadora Nacional de Tecnología Educativa\r\nDirección de Tecnologías Educativas\r\nInstituto Profesional AIEP\r\n(56-2) 29022772\r\naiep.cl";
				FormatText = "<head>\r\n<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />\r\n\r\n<style type='text/css'>\r\n<!--\r\n.Estilo1 {\r\n	font-family: Arial, Helvetica, sans-serif;\r\n	font-size: 14px;\r\n	color: #0A75B6;\r\n}\r\n.Estilo2 {\r\n	color: #FF0000;\r\n	font-weight: bold;\r\n}\r\n.Estilo3 {\r\n	font-family: Arial, Helvetica, sans-serif;\r\n	color: #CB1703\r\n	}\r\n.Estilo4 {font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #0A75B6; font-weight: bold; }\r\n-->\r\n</style>\r\n</head>\r\n\r\n<body>\r\n<div class='Estilo1'><strong>Estimad@</strong> [nombre_estudiante] </div>\r\n<div class='Estilo1'>&nbsp;</div>\r\n<div align='justify' class='Estilo1'>\r\n  <p>Junto con saludarle y esperando que se encuentre bien, hemos detectado que desde que se iniciaron las clases no tiene conexi&oacute;n en la plataforma Semipresencial y nos interesa saber las razones de su ausencia. Es muy importante que pueda informarnos cu&aacute;l es el motivo de esta situaci&oacute;n, de modo de poder asistirle y orientarlo sobre los pasos a seguir para regularizar su situaci&oacute;n.</p>\r\n  <p><br />\r\n    Atenta a sus comentarios.</p>\r\n</div>\r\n<div class='Estilo4'>Carolina Mardones I.<br>\r\nCoordinadora Nacional de Tecnolog&iacute;a Educativa<br>\r\nDirecci&oacute;n de Tecnolog&iacute;as Educativas<br>\r\nInstituto Profesional AIEP<br>\r\n(56-2) 29022772</div>\r\n\r\n <div align='left' class='Estilo2'><a href='http://www.aiep.cl/' target='_blank' class='Estilo3'>aiep.cl</a></div>\r\n</div>\r\n</body>";

			}
			if (Format.equals("2")) {

				//PlainText = "Estimad@ [nombre_estudiante] \r\n \r\nJunto con saludarle y esperando que se encuentre bien, hemos detectado que ya hace una semana no tiene conexión en la plataforma Semipresencial y nos interesa saber las razones de su ausencia; \r\nRecuerda que tu participación semanal es muy importante  dentro de esta modalidad de estudio para poder cumplir con las actividades y fechas establecidas en la plataforma para así poder tener la  asistencia correspondiente a los días sábados\r\n\r\n\r\nAtenta a sus comentarios.\r\n\r\nCarolina Mardones I.\r\nCoordinadora Nacional de Tecnología Educativa\r\nDirección de Tecnologías Educativas\r\nInstituto Profesional AIEP\r\n(56-2) 29022772\r\naiep.cl";
				FormatText = "<head>\r\n<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />\r\n\r\n<style type='text/css'>\r\n<!--\r\n.Estilo1 {\r\n	font-family: Arial, Helvetica, sans-serif;\r\n	font-size: 14px;\r\n	color: #0A75B6;\r\n}\r\n.Estilo2 {\r\n	color: #FF0000;\r\n	font-weight: bold;\r\n}\r\n.Estilo3 {font-family: Arial, Helvetica, sans-serif; color: #CB1703}\r\n.Estilo4 {font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #0A75B6; font-weight: bold; }\r\n-->\r\n</style>\r\n</head>\r\n\r\n<body>\r\n<div class='Estilo1'><strong>Estimad@</strong> [nombre_estudiante] </div>\r\n<div class='Estilo1'>&nbsp;</div>\r\n\r\n<div align='justify' class='Estilo1'>\r\n  <p>Junto con saludarle y esperando que se encuentre bien, hemos detectado que ya hace una semana&nbsp;no tiene&nbsp;conexi&oacute;n en la plataforma Semipresencial y nos interesa saber las razones de su ausencia; Recuerda que tu participaci&oacute;n semanal es muy importante &nbsp;dentro de esta modalidad de estudio para poder cumplir con las actividades y fechas establecidas en la plataforma para as&iacute; poder tener la &nbsp;asistencia correspondiente a los d&iacute;as s&aacute;bados</p>\r\n  <p><br />\r\n    Atenta a sus comentarios.</p>\r\n</div>\r\n<div class='Estilo4'>Carolina Mardones I.<br>\r\nCoordinadora Nacional de Tecnolog&iacute;a Educativa<br>\r\nDirecci&oacute;n de Tecnolog&iacute;as Educativas<br>\r\nInstituto Profesional AIEP<br>\r\n(56-2) 29022772</div>\r\n\r\n <div align='left' class='Estilo2'><a href='http://www.aiep.cl/' target='_blank' class='Estilo3'>aiep.cl</a></div>\r\n</div>\r\n</body>\r\n";
			}
			if (Format.equals("3")) {

				//PlainText = "Estimad@ [nombre_estudiante] \r\nJunto con saludarle y esperando que se encuentre bien, hemos detectado que ya hace dos semanas no tienes conexión en la plataforma Semipresencial y nos interesa saber las razones de su ausencia.\r\nEs muy importante que pueda infórmanos cuál es el motivo de esta situación, de modo de poder asistirle y orientarlo sobre los pasos a seguir para regularizar su situación.\r\n\r\n\r\nAtenta a sus comentarios.\r\n\r\nCarolina Mardones I.\r\nCoordinadora Nacional de Tecnología Educativa\r\nDirección de Tecnologías Educativas\r\nInstituto Profesional AIEP\r\n(56-2) 29022772\r\naiep.cl";
				FormatText = "<head>\r\n<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />\r\n\r\n<style type='text/css'>\r\n<!--\r\n.Estilo1 {\r\n	font-family: Arial, Helvetica, sans-serif;\r\n	font-size: 14px;\r\n	color: #0A75B6;\r\n}\r\n.Estilo2 {\r\n	color: #FF0000;\r\n	font-weight: bold;\r\n}\r\n.Estilo3 {font-family: Arial, Helvetica, sans-serif; color: #CB1703}\r\n.Estilo4 {font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #0A75B6; font-weight: bold; }\r\n-->\r\n</style>\r\n</head>\r\n\r\n<body>\r\n<div class='Estilo1'><strong>Estimad@</strong> [nombre_estudiante] </div>\r\n\r\n<div align='justify' class='Estilo1'>\r\n  <p>Junto con saludarle y esperando que se encuentre bien,  hemos detectado que ya hace dos semanas no tienes conexi&oacute;n en la plataforma Semipresencial y nos interesa saber las razones de su ausencia. Es muy importante que pueda informarnos cu&aacute;l es el motivo de esta situaci&oacute;n, de modo de poder asistirle y orientarlo sobre los pasos a seguir para regularizar su situaci&oacute;n.</p>\r\n\r\n<br />\r\n    Atenta a sus comentarios.</p>\r\n</div>\r\n<div class='Estilo4'>Carolina Mardones I.<br>\r\nCoordinadora Nacional de Tecnolog&iacute;a Educativa<br>\r\nDirecci&oacute;n de Tecnolog&iacute;as Educativas<br>\r\nInstituto Profesional AIEP<br>\r\n(56-2) 29022772</div>\r\n\r\n <div align='left' class='Estilo2'><a href='http://www.aiep.cl/' target='_blank' class='Estilo3'>aiep.cl</a></div>\r\n</div>\r\n</body>\r\n";
			}
			if (Format.equals("4")) {

				//PlainText = "Estimad@ [nombre_estudiante] \r\nBienvenido a AIEP!\r\n\r\n Junto con saludarte, queremos aprovechar esta instancia para darte las primeras informaciones respecto de las actividades e inicio de clases del periodo académico.\r\n\r\nTe comentamos que recibirás un correo con tu usuario y contraseÃ±a para ingresar a la plataforma Semipresencial, la cual se encuentra en la dirección http://semipresencial.aiep.cl\r\nCon estos mismos datos podrás acceder a tu intranet académica, en donde podrás acceder a información institucional y a tu cuenta de correo electrónico AIEP. \r\nRevisa el sitio http://www.aiep.cl para ingresar a tu intranet o hazlo directamente en http://intranet.aiep.cl\r\n\r\nPara más información de uso de la plataforma Semipresencial visita : http://semipresencial.aiep.cl/pluginfile.php/7764/block_html/content/carta_bienvenida_estudiantes.pdf\r\n\r\n\r\nPor favor, confirma la recepción de este correo e indicamos si necesitas cambiar tu correo de contacto. Es muy importante asegurar canales de comunicación efectivos.\r\n\r\nEn caso de requerir ayuda sobre el uso de la plataforma o realizar consultas en general puedes enviar tus solicitudes a:\r\n\r\nCorreo de la modalidad : online@aiep.cl\r\nMesa de Ayuda : http://soporte.aiep.cl\r\n\r\nNos contactaremos contigo a la brevedad\r\n\r\n\r\nDirección de Tecnologías Educativas\r\nDirección Nacional de Desarrollo Académico\r\nVicerrectoría Académica \r\nInstituto Profesional AIEP";
				FormatText = "<head>\r\n<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />\r\n\r\n<style type='text/css'>\r\n<!--\r\n.Estilo1 {\r\n	font-family: Arial, Helvetica, sans-serif;\r\n	font-size: 14px;\r\n	color: #0A75B6;\r\n	text-align: justify;\r\n}\r\n.Estilo2 {\r\n	color: #FF0000;\r\n	font-weight: bold;\r\n}\r\n.Estilo3 {\r\n	font-family: Arial, Helvetica, sans-serif;\r\n	color: #CB1703\r\n	}\r\n.Estilo4 {font-family: Arial, Helvetica, sans-serif; font-size: 14px; color: #0A75B6; font-weight: bold; }\r\n-->\r\n</style>\r\n</head>\r\n\r\n<body>\r\n<div class='Estilo1'><strong>Estimad@</strong> [nombre_estudiante] </div>\r\n<div class='Estilo1'></div>\r\n<div align='justify' class='Estilo1'>\r\n  <p>Bienvenido a la modalidad PEV Semipresencial</p>\r\n  <p>Junto con saludarte, queremos aprovechar esta instancia para darte las primeras informaciones respecto de las actividades e inicio de clases del periodo acad&eacute;mico.</p>\r\n  <p>Te comentamos que  recibir&aacute;s un correo con tu usuario y contrase&ntilde;a para ingresar a la plataforma Semipresencial, la cual se encuentra en la direcci&oacute;n <a href='http://semipresencial.aiep.cl'>http://semipresencial.aiep.cl</a></p>\r\n  <p>    Con estos mismos datos podr&aacute;s acceder a tu intranet acad&eacute;mica, en donde podr&aacute;s acceder a informaci&oacute;n institucional y a tu cuenta de correo electr&oacute;nico AIEP. <br>\r\n    Revisa el sitio <a href='http://www.aiep.cl'>http://www.aiep.cl</a> para ingresar a tu intranet o hazlo directamente en <a href='http://intranet.aiep.cl'>http://intranet.aiep.cl</a> </p>\r\n  <p>Para m&aacute;s informaci&oacute;n visita : <a href='http://semipresencial.aiep.cl/pluginfile.php/7764/block_html/content/carta_bienvenida_estudiantes.pdf'>http://semipresencial.aiep.cl/pluginfile.php/7764/block_html/content/carta_bienvenida_estudiantes.pdf</a></p>\r\n  <p><br>\r\n    Por favor, confirma la recepci&oacute;n de este correo e indicamos si necesitas cambiar tu correo de contacto. Es muy importante asegurar canales de comunicaci&oacute;n efectivos.</p>\r\n  <p>En caso de requerir ayuda sobre el uso de la plataforma o realizar consultas en general  puedes enviar tus solicitudes a:<br>\r\n    Correo de la modalidad : semipresencial@aiep.cl<br>\r\n  Mesa de Ayuda :  http://soporte.aiep.cl</p>\r\n  <p>Nos contactaremos contigo a la brevedad<br>\r\n  </p>\r\n</div>\r\n<div class='Estilo4'>Direcci&oacute;n de Tecnolog&iacute;as Educativas<br>\r\n  Direcci&oacute;n Nacional de Desarrollo Acad&eacute;mico<br>\r\n  Vicerrector&iacute;a Acad&eacute;mica <br>\r\nInstituto Profesional AIEP</div>\r\n</div>\r\n</body>";
			}

			model.addAttribute("EmailMessage", FormatText);
			model.addAttribute("Emails", Emails);
			model.addAttribute("Ids", Ids);
			model.addAttribute("Names", Names);
			model.addAttribute("Status", Status);
			model.addAttribute("Method", "GET");
		} catch (Exception e) {

			e.printStackTrace();
		}

		return "SendEmail";
	}
	
	@RequestMapping(value = { "SendEmail" }, method = RequestMethod.POST)
	public String PostSendEmail(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

			Context ctx = ContextManagerFactory.getInstance().getContext();
			ContextManagerFactory.getInstance().setContext(request);
			
			String[] Emails = request.getParameter("Emails").split(",");
			String[] Ids = request.getParameter("Ids").split(",");
			String[] Names = request.getParameter("Names").split(",");
			String[] Status = request.getParameter("Status").split(",");
			String Message = request.getParameter("EmailMessage");
			String testEmail = request.getParameter("testEmail");
			boolean test = Boolean.getBoolean(testEmail);
			
			boolean error = false;
			if (Emails != null && Message != null) {

				if (Emails.length > 0) {
					
					try {
						SendEmailtoStudents(Emails, Message, Names, Ids, Status, test);
					} catch (ConnectionNotAvailableException e) {
						error = true;
						e.printStackTrace();
					} catch (InstantiationException e) {
						error = true;
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						error = true;
						e.printStackTrace();
					} catch (MessagingException e) {
						error = true;
						e.printStackTrace();
					} catch (ValidationException e) {
						error = true;
						e.printStackTrace();
					} catch (SQLException e) {
						error = true;
						e.printStackTrace();
					}
				}
			}
			
		model.addAttribute("Method", "POST");
		
		if(error) {
			System.out.println("Sucedio un error al intentar enviar correo");
			model.addAttribute("Error","true");
		} else
			model.addAttribute("Error","false");
		
		
		return "SendEmail";
	}

	@RequestMapping(value = { "StudentManagement" }, method = RequestMethod.GET)
	public String GetStudentManagement(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

		try {

			Context ctx = ContextManagerFactory.getInstance().getContext();
			ContextManagerFactory.getInstance().setContext(request);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return "StudentManagement";
	}

	@RequestMapping(value = { "ConfirmManagement" }, method = RequestMethod.GET)
	public String GetConfirmManagement(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

		try {
			Context ctx = ContextManagerFactory.getInstance().getContext();
			ContextManagerFactory.getInstance().setContext(request);

			String Email = (String) request.getParameter("Email");
			String Id = (String) request.getParameter("Id");
			String DateSent = (String) request.getParameter("Date");
			String Status = (String) request.getParameter("Status");
			String Type = (String) request.getParameter("Type");
			String Contact_Channel = (String) request.getParameter("Contact_Channel");
			String Student_Response = (String) request.getParameter("Student_Response");
			String Observations = (String) request.getParameter("Observations");
			String Edit = (String) request.getParameter("Edit");
			String Results = "";

			if (Type.equals("C")) {

				Student_Response = " ";
			}
			if (Edit == null) {

				Edit = "false";
			}

			ConnectionManager cManager = null;
			Connection conn = null;
			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();

			Results = RegisterManagement(Id, DateSent, Status, Contact_Channel, Observations, Type, Student_Response,
					"Manual", conn, Edit);

			conn.close();

			cManager.close();
			String Sent = "true";

			if (Results.indexOf("Error:") != -1) {

				Sent = "false";
			}

			model.addAttribute("Sent", Sent);
			model.addAttribute("Id", Id);
			model.addAttribute("Type", Type);
			model.addAttribute("Edit", Edit);
			model.addAttribute("Results", Results);
			model.addAttribute("Student_Response", Student_Response);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return "ConfirmManagement";
	}
	
	@RequestMapping(value = { "CourseControlInfo" }, method = RequestMethod.GET)
	public String GetCourseControlInfo(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

		try {

			ContextManagerFactory.getInstance().setContext(request);
			
			String Headquarter = request.getParameter("Sede");
			String Modalidad = request.getParameter("Modalidad");
			String pageString = request.getParameter("currentPage");
			String HTMLTable = "You Selected an invalid option";
			int currentPage = Integer.valueOf(pageString != null ? pageString : "1");			
			String CourseFilter = request.getParameter("CourseFilter");
			
			HTMLTable = getCourses(Headquarter, currentPage, Modalidad,CourseFilter);
			
			model.addAttribute("HTMLTable", HTMLTable);
			
		} catch (Exception e) {

			e.printStackTrace();
		}

		return "CourseControlInfo";
	}


	@RequestMapping(value = { "StudentRecord" }, method = RequestMethod.GET)
	public String GetStudentRecord(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

		try {
			Context ctx = ContextManagerFactory.getInstance().getContext();
			ContextManagerFactory.getInstance().setContext(request);

			String Id = (String) request.getParameter("Id");

			String HTMLData = "El estudiante no existe";

			if (Id != null) {

				HTMLData = getStudentRecord(Id);
			}

			if (HTMLData.equals("")) {

				HTMLData = "<table width=\"99%\" border=\"0\" class=\"tablaHist\" align=\"center\">" + "  <tbody>"
						+ "  	<tr>"
						+ "		<td class=\"titleColumna\">No se ha realizado gestión para este Estudiante</td>"
						+ "	</tr>  " + "  </tbody>" + "</table>";
			}

			model.addAttribute("HTMLData", HTMLData);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return "StudentRecord";
	}

	@RequestMapping(value = { "DownloadReport" }, method = RequestMethod.GET)
	public String saveForm1(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		String Report = (String) request.getParameter("Report");
		String Headquarter = (String) request.getParameter("Headquarters");
		String Modalidad = (String) request.getParameter("Modalidades");
		String pk1 = (String) request.getParameter("pk1");
		//PrintWriter out = null;
		String Results = "Results...";
		//System.out.format("EXCEL REPORT - Headquarter=%s, Modalidad=%s%n ",Headquarter,Modalidad);
		try {

			if (Report != null) {

				if (Report.equals("Management") && Headquarter != null) {

					Results = GetManagementXls(Headquarter, Modalidad);
				}
				if (Report.equals("Record") && pk1 != null) {

					Results = GetRecordXls(pk1);
				}
			} else {

				Results = "Error";
			}

		} catch(Exception ex){
			ex.printStackTrace();
			Results += ex.getMessage();
		}
		finally {
			
			// out.close();
		}
		request.setAttribute("Headquarter", Headquarter);
		model.addAttribute("Results", Results);
		model.addAttribute("Modalidad", Modalidad);

		return "DownloadReport";
	}
	
	@RequestMapping(value = { "InstructorsControlReport" }, method = RequestMethod.GET)
	public String getInstructorControlReport(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		String Sede = request.getParameter("Sede");
		String Modulo = request.getParameter("Modulo");
		String Seccion = request.getParameter("Seccion");
		String Anio = request.getParameter("Anio");
		String Semestre = request.getParameter("Semestre");
		String Modalidad = request.getParameter("Modalidad");
		
		Modulo = (Modulo.equals("Todo") || Modulo == null) ? "%" : Modulo;
		Seccion = (Seccion.isEmpty() || Seccion == null) ? "%" : Seccion;
		Anio = (Anio.equals("Todo") || Anio == null) ? "%" : Anio;
		Semestre = (Semestre.equals("Todo") || Semestre == null) ? "%" : Semestre;
		Modalidad = (Modalidad.equals("Todo") || Modalidad == null) ? "%" : Modalidad;
		
		String CourseId = Modulo+"-"+Seccion+"-"+Anio+"-"+Semestre+"-"+Modalidad;
		String Results = "Results...";
		String htmlTable = "";
		try {
			htmlTable = this.getInstructorActivityReport(CourseId, Sede);
			Results = htmlTable;
		} catch(Exception ex){
			ex.printStackTrace();
			Results += ex.getMessage();
		}
		
		model.addAttribute("HTMLTable", Results);

		return "InstructorsControlReport";
	}

	@RequestMapping(value = { "CourseControl" }, method = RequestMethod.GET)
	public String GetCourseControl(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

		try {

			//Context ctx = ContextManagerFactory.getInstance().getContext();
			ContextManagerFactory.getInstance().setContext(request);
			
			String HTMLHeadquarters = this.getCoursesHeadquarters();
			String HTMLModalidades = getCourseModalities();
			String HTMLModules = getModulos();
			String HTMLYears = getAnios();
			String HTMLSemeters = getSemestres();

			String ReportName = "Seguimiento de Cursos";

			model.addAttribute("HTMLHeadquarters", HTMLHeadquarters);
			model.addAttribute("HTMLModalidades", HTMLModalidades);
			model.addAttribute("HTMLModules", HTMLModules);
			model.addAttribute("HTMLYears", HTMLYears);
			model.addAttribute("HTMLSemeters", HTMLSemeters);
			model.addAttribute("PageSize", pageSize);
			model.addAttribute("ReportName", ReportName);
			
		} catch (Exception e) {

			e.printStackTrace();
		}

		return "CourseControl";
	}

	@RequestMapping(value = { "StudentActivity" }, method = RequestMethod.GET)
	public String GetStudentActivity(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

		try {

			Context ctx = ContextManagerFactory.getInstance().getContext();
			ContextManagerFactory.getInstance().setContext(request);

			String Id = request.getParameter("Id");
			String Name = request.getParameter("Name");
			String Status = request.getParameter("Status");

			String HTMLStuActivity = getStudentActivity(Id);
			String ReportName = "Control de Actividad de Estudiantes";

			model.addAttribute("Id", Id);
			model.addAttribute("Name", Name);
			model.addAttribute("Status", Status);
			model.addAttribute("ReportName", ReportName);
			model.addAttribute("HTMLStuActivity", HTMLStuActivity);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return "StudentActivity";
	}

	@RequestMapping(value = { "InstructorControl" }, method = RequestMethod.GET)
	public String GetInstructorControl(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

		try {

			ContextManagerFactory.getInstance().setContext(request);
			
			String ReportName = "Control de Docentes";
			String HTMLSedes = this.getCoursesHeadquarters();
			String HTMLModulos = this.getModulos();
			String HTMLAnios = this.getAnios();
			String HTMLSemestres = this.getSemestres();
			String HTMLModalidades = this.getCourseModalities();
			
			model.addAttribute("ReportName", ReportName);
			model.addAttribute("HTMLSedes", HTMLSedes);
			model.addAttribute("HTMLModulos", HTMLModulos);
			model.addAttribute("HTMLAnios", HTMLAnios);
			model.addAttribute("HTMLSemestres", HTMLSemestres);
			model.addAttribute("HTMLModalidades", HTMLModalidades);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return "InstructorControl";

	}

	@RequestMapping(value = { "InstructorAccess" }, method = RequestMethod.GET)
	public String GetInstructorAccess(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

		try {

			Context ctx = ContextManagerFactory.getInstance().getContext();
			ContextManagerFactory.getInstance().setContext(request);

			String HTMLHeadquarters = this.getCoursesHeadquarters();
			String HTMLModalidades = getCourseModalities();
			String HTMLModules = getModulos();
			String HTMLYears = getAnios();
			String HTMLSemeters = getSemestres();

			String ReportName = "Control de Accesso de Docentes";

			model.addAttribute("HTMLHeadquarters", HTMLHeadquarters);
			model.addAttribute("HTMLModalidades", HTMLModalidades);
			model.addAttribute("HTMLModules", HTMLModules);
			model.addAttribute("HTMLYears", HTMLYears);
			model.addAttribute("HTMLSemeters", HTMLSemeters);
			model.addAttribute("PageSize", pageSize);
			model.addAttribute("ReportName", ReportName);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return "InstructorAccess";

	}

		
	@RequestMapping(value = { "InstructorsInfo" }, method = RequestMethod.GET)
	public String GetInstructorsInfo(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

		try {
			ContextManagerFactory.getInstance().setContext(request);

			String Headquarter = request.getParameter("Sede");
			String Report = request.getParameter("Report");
			String Modalidad = request.getParameter("Modalidad");
			String pageString = request.getParameter("currentPage");
			String HTMLTable = "You Selected an invalid option";
			
			String Modulo = request.getParameter("Modulo");
			String Seccion = request.getParameter("Seccion");
			String Semestre = request.getParameter("Semestre");
			String Anio = request.getParameter("Anio");
			String irBtnAction = "";
			
			String courseId = Modulo + "-" + Seccion + "-" + Anio + "-" + Semestre + "-" + Modalidad;
			
			int currentPage = Integer.valueOf(pageString != null ? pageString : "1");			
			String HTMLPages = "";

			if (Report != null) {

				if (Report.equals("Activity")) {
					
					irBtnAction = "selectPage(pageNumber.value)";
					HTMLTable = getInstructorActivity(courseId, currentPage, Headquarter);
					HTMLPages = this.getTablePagesInstructorActivity(courseId, Headquarter);
					
				}
				if (Report.equals("Access")) {
					
					String CourseFilter = request.getParameter("CourseFilter");
					irBtnAction = "BtnFetchInfo(pageNumber.value,'Ir')";
					HTMLTable = getInstructorAccess(Headquarter,currentPage,Modalidad,CourseFilter);
					HTMLPages = "<label> de <label id='TotalPages'></label> paginas.</label>";
				}
			}

			model.addAttribute("HTMLTable", HTMLTable);
			model.addAttribute("tablePages", HTMLPages);		
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("IrAction", irBtnAction);
			

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return "InstructorsInfo";
	}

	
	 @RequestMapping(value = { "InstructorAccessReport" }, method = RequestMethod.GET)
	public String GetInstructorAccessReport(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

		try {

			Context ctx = ContextManagerFactory.getInstance().getContext();
			ContextManagerFactory.getInstance().setContext(request);

			String Headquarter = request.getParameter("Sede");
			String Modalidad = request.getParameter("Modalidad");
			int firstRow = Integer.parseInt(request.getParameter("firstRow"));
			int lastRow = Integer.parseInt(request.getParameter("lastRow"));
			
			String HTMLTable = "You Selected an invalid option";
			HTMLTable = getInstructorAccessReportPerInterval(Headquarter,Modalidad,firstRow,lastRow);
			

			model.addAttribute("HTMLTable", HTMLTable);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return "InstructorAccessReport";

	}
	
	@RequestMapping(value = { "DownloadInstructorAccessReport" }, method = RequestMethod.GET)
	public String getDownloadInstructorAccessReport(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		//String HTMLTable = request.getParameter("HTMLTable");
		
		String Headquarter = request.getParameter("Sede");
		String Modalidad = request.getParameter("Modalidad");
		String CourseFilter = request.getParameter("CourseFilter");
		
		String HTMLTable = getInstructorAccessWholeReport(Headquarter, Modalidad,CourseFilter);
		
		model.addAttribute("HTMLTable", HTMLTable);
		model.addAttribute("Headquarter", Headquarter);
		model.addAttribute("Modalidad", Modalidad);

		return "DownloadInstructorAccessReport";
	}
	
	@RequestMapping(value = { "InstructorAccessGetRowCount" }, method = RequestMethod.GET)
	public String GetInstructorAccessGetRowCount(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {

		try {

			Context ctx = ContextManagerFactory.getInstance().getContext();
			ContextManagerFactory.getInstance().setContext(request);

			String Headquarter = request.getParameter("Sede");
			String Modalidad = request.getParameter("Modalidad");
			String CourseFilter = request.getParameter("CourseFilter");
			int Count = 0;
			Count = getInstructorAccessRowCount(Headquarter,Modalidad, CourseFilter);
			

			model.addAttribute("Count", Count);
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		return "InstructorAccessGetRowCount";

	}
	
	@RequestMapping(value = { "StudentSettings" }, method = RequestMethod.GET)
	public String getSettings(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {
		
		ConnectionManager cManager = null;
		Connection conn = null;
		
		try {
			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();
			
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM LNOH_STUDREPORT_SETTINGS");
			
			int estadoVerde = 0;
			int estadoAmarillo = 0;
			int estadoNaranja = 0;
			
			if(rs.next()){
				estadoVerde = rs.getInt("dias_estado_verde");
				estadoAmarillo = rs.getInt("dias_estado_amarillo");
				estadoNaranja = rs.getInt("dias_estado_naranja");
			} else {
				estadoVerde = 7;
				estadoAmarillo = 14;
				estadoNaranja = 14;
			}
			
			rs.close();
			rs = null;
			
			model.addAttribute("estadoVerde",estadoVerde);
			model.addAttribute("estadoAmarillo", estadoAmarillo);
			model.addAttribute("estadoNaranja", estadoNaranja);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		cManager.releaseConnection(conn);
		
		return "StudentSettings";

	}
	
	@RequestMapping(value = { "StudentSettings" }, method = RequestMethod.POST)
	public String getSettingsPost(HttpServletRequest request, HttpServletResponse response, final ModelMap model)
			throws UnsupportedEncodingException, PersistenceException, IOException {
		
		ConnectionManager cManager = null;
		Connection conn = null;
		
		int estadoVerde = 7;
		int estadoAmarillo = 14;
		int estadoNaranja = 14;
		
		try {
			Object test = request.getParameterMap().keySet().toArray()[0];
			String verde = request.getParameter("diasVerde");
			String amarillo = request.getParameter("diasAmarillo");
			String naranja = request.getParameter("diasNaranja");
			
			estadoVerde = Integer.valueOf(verde == null? "0": verde);
			estadoAmarillo = Integer.valueOf(amarillo == null ? "0" : amarillo);
			estadoNaranja = Integer.valueOf(naranja == null ? "0" : naranja);
			
			cManager = BbDatabase.getDefaultInstance().getConnectionManager();
			conn = cManager.getConnection();
			
			String query = "INSERT INTO LNOH_STUDREPORT_SETTINGS VALUES (LNOH_STUDREPORT_SETTINGS_SEQ.nextval, {verde}, {amarillo}, {naranja})";
			query = query.replace("{verde}", String.valueOf(estadoVerde));
			query = query.replace("{amarillo}", String.valueOf(estadoAmarillo));
			query = query.replace("{naranja}", String.valueOf(estadoNaranja));
			
			System.out.println(query);
			conn.createStatement().executeQuery("DELETE FROM LNOH_STUDREPORT_SETTINGS");
			conn.createStatement().executeQuery(query);
			
			model.addAttribute("estadoVerde",estadoVerde);
			model.addAttribute("estadoAmarillo", estadoAmarillo);
			model.addAttribute("estadoNaranja", estadoNaranja);
			
			cManager.releaseConnection(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "StudentSettings";

	}
	
	@RequestMapping(value = { "StudentActivityReport" }, method = RequestMethod.POST)
	public String getStudentActivityReport(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

		String HTMLTable = request.getParameter("HTMLTable");

		model.addAttribute("HTMLTable", HTMLTable);

		return "StudentActivityReport";
	}
}// fin class