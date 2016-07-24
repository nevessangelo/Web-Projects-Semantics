package uff.ic.swlab;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFLanguages;

public class RDFTranslator {

	public static void write(OutputStream output, Model model, Lang lang) {
		if (lang == RDFLanguages.nameToLang("RDFa"))
			writeRDFa(output, model);
		else if (lang == null)
			writeHTML(output, model);
	}

	public static void writeRDFa(OutputStream output, Model model) {
		StringWriter writer = new StringWriter();
		model.write(writer, Lang.RDFXML.getName());

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://rdf-translator.appspot.com/convert/detect/rdfa/content");
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("content", writer.toString()));
		try {
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// EntityUtils to get the response content
				String content = EntityUtils.toString(entity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void writeHTML(OutputStream output, Model model) {
		String response = "";
		response += "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">";
		response += "<html>";
		response += "<head>";
		response += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">";
		response += "<title>Entity description</title>";
		response += "<link href=\"/swlab.css\" rel=\"stylesheet\" type=\"text/css\" />";
		response += "</head>";
		response += "<body>";
		response += "<center>";
		response += "<h1>Entity description</h1>";
		response += "</center>";
		response += "<br>";
		response += "<br>";
		response += "<br>";

		response += "<table align=\"center\" border=\"0\" style=\"width: 70%\">\n";
		response += "<tr>\n";
		response += "<td style=\"width: 50%\">\n";
		response += "<h2>Satements</h2>\n";

		response += "<table cellpadding=\"5\" align=\"center\" border=\"2\">\n";
		response += "<tr><th>subject</th><th>predicate</th><th>object</th></tr>\n";
		StmtIterator iter = model.listStatements();
		while (iter.hasNext()) {
			Statement stmt = iter.nextStatement();
			Resource subject = stmt.getSubject();
			Property predicate = stmt.getPredicate();
			RDFNode object = stmt.getObject();

			String data1 = "<td><a href=\"%1s\">%2s</a></td>\n";
			String data2 = "<td>%1s</td>\n";
			String data3 = null;
			String data31 = "<td><a href=\"%1s\">%2s</a></td>\n";
			String data32 = "<td>%1s</td>\n";
			data1 = String.format(data1, subject.toString(), subject.toString());
			data2 = String.format(data2, predicate.toString());
			if (object instanceof Resource)
				data3 = String.format(data31, object.toString(), object.toString());
			else
				data3 = String.format(data32, " \"" + object.toString() + "\"");

			response += "<tr>\n";
			response += data1 + data2 + data3;
			response += "</tr>\n";
		}
		response += "</table>\n";

		response += "</td>\n";
		response += "</tr>\n";
		response += "</table>\n";

		response += "</body>";
		response += "</html>";

		
		try {
			OutputStreamWriter writer = new OutputStreamWriter(output);
			writer.write(response);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
