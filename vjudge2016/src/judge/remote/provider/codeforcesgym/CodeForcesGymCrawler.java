package judge.remote.provider.codeforcesgym;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;
import org.apache.http.HttpHost;
import org.springframework.stereotype.Component;

import judge.executor.ExecutorTaskType;
import judge.executor.Task;
import judge.httpclient.DedicatedHttpClient;
import judge.httpclient.HttpStatusValidator;
import judge.remote.RemoteOjInfo;
import judge.remote.crawler.RawProblemInfo;
import judge.remote.shared.FileDownloader;
import judge.remote.shared.codeforces.CFStyleCrawler;

@Component
public class CodeForcesGymCrawler extends CFStyleCrawler {

	@Override
	public RemoteOjInfo getOjInfo() {
		return CodeForcesGymInfo.INFO;
	}

	@Override
	protected String getProblemUrl(String problemId) {
		String contestNum = problemId.replaceAll("\\D.*", "");
		String problemNum = problemId.replaceAll("^\\d*", "");
		return getHost().toURI() + "/gym/" + contestNum + "/problem/" + problemNum;
	}

	@Override
	protected void preValidate(String problemId) {
		Validate.isTrue(problemId.matches("\\d{6}[A-Z].*"));
		Validate.isTrue(problemId.toUpperCase().equals(problemId));
	}

	@Override
	protected void populateProblemInfo(RawProblemInfo info, String problemId, String html) throws Exception {

        
		try {
		    //try to craw html description as same as Codeforeces
	        super.populateProblemInfo(info, problemId, html);
		} catch (Exception e) { 
		    //Not have html description,try to craw from contest problem list and download PDF
		    final String problemNum = problemId.replaceAll("^\\d*", "");
			final String contestNum = problemId.replaceAll("\\D.*", "");
			final HttpHost host = new HttpHost("codeforces.com");
			final DedicatedHttpClient client = dedicatedHttpClientFactory.build(host);
			final String contestUrl = host.toURI() + "/gym/" + contestNum;

			Task<String> taskDescription = new Task<String>(ExecutorTaskType.GENERAL) {
				@Override
				public String call() throws Exception {
					String html = client.get(contestUrl, HttpStatusValidator.SC_OK).getBody();
					return html;
				}
			};

			taskDescription.submit();
			String htmlDescription = taskDescription.get();

			String regex = "<a href=\"\\/gym\\/" + contestNum + "\\/problem\\/" + problemNum
					+ "\"><!--\\s*-->([^<]+)(?:(?:.|\\s)*?<div){2}[^>]*>\\s*([^<]+)<\\/div>\\s*(\\d+)\\D*(\\d+)";

			Matcher matcher = Pattern.compile(regex).matcher(htmlDescription);
			matcher.find();

			info.title = matcher.group(1);
			info.timeLimit = (int) (Double.parseDouble(matcher.group(3)) * 1000);
			info.memoryLimit = Integer.parseInt(matcher.group(4)) * 1024;
			info.source = "";
			info.url = contestUrl;

			String io = matcher.group(2);
			regex = "\\/gym\\/" + contestNum + "\\/attachments\\/download\\S*?\\.pdf";

			matcher = Pattern.compile(regex).matcher(html);
			matcher.find();
			
			String pdfURI;
			try{
			    String path = this.getClass().getResource("/").getPath().
			            replace("WEB-INF/classes/", "problem/pdf" +  matcher.group(0).substring(0,matcher.group(0).lastIndexOf("/")));
	            FileDownloader.downLoadFromUrl(host.toURI() + matcher.group(0),path);
	            pdfURI =  "pdf/" + matcher.group(0).substring(1);
			} catch (Exception e1) {
			    pdfURI = host.toURI() + matcher.group(0);
            }
			info.description = "<strong>Input/Output:<br>" +  io + "</strong>"  + "<br>" +
			        "<p><a href=\"" + pdfURI + "\">Click here to download PDF file.</a></p><hr>" + 
                    "<p><embed width=\"100%\" height=\"700\" src=\"" + pdfURI + "\"> </embed></p> ";
			info.input = "";
			info.output = "";
			info.sampleInput = "";
			info.sampleOutput = "";
		}

	}

}
