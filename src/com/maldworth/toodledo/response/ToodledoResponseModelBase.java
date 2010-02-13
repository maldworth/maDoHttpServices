package com.maldworth.toodledo.response;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.maldworth.httpservices.BasicXmlResponseModel;

public abstract class ToodledoResponseModelBase extends BasicXmlResponseModel
{
	private boolean _isError;
	private String _errorMessage;
	
	public ToodledoResponseModelBase()
	{
		super(true);//The static class in here also has a variable for isAndroid, so remember that
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isError(String unParsedEntity)
	{
		if(unParsedEntity.contains("<error>") && unParsedEntity.contains("</error>"))
		{
			handleError();
			return true;
		}
		return false;
	}
	
	public void handleError()
	{
		ErrorXmlHandler eh = new ErrorXmlHandler();
		
		try
		{
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			
			sp.parse(new InputSource(new StringReader(getUnParsedEntity())),eh);
		}
		catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			_isError = true;
			_errorMessage = eh._errorMessage;
		}
	}

	/**
	 * @param True if there was an error with the Response
	 */
	public boolean isError() {
		return _isError;
	}
	
	public String errorMessage()
	{
		return _errorMessage;
	}
	
	static class ErrorXmlHandler extends DefaultHandler
	{
		private boolean _isAndroid = true;//CHANGE TO false if using on Desktop
		private boolean _inErrorTag = false;
		private String _errorMessage = "This shouldn't happen. Let maldworth know.";

		@Override
		public void characters(char[] ch, int start, int length)
		{
			if(_inErrorTag)
				_errorMessage = new String(ch, start, length);
		}

		@Override
		public void endElement(String namespaceURI, String localName,
				String qName) throws SAXException
		{
			//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
			String theTagName = (_isAndroid == true)? localName : qName;
			
			if("error".equalsIgnoreCase(theTagName))
				_inErrorTag = false;
		}

		@Override
		public void startElement(String namespaceURI, String localName,
				String qName, Attributes atts) throws SAXException
		{
			//This line is added due to fact that android sax parser will have the response in localName, but if I run it locally in a java console app, than qName is what works.
			String theTagName = (_isAndroid == true)? localName : qName;
			
			if("error".equalsIgnoreCase(theTagName))
				_inErrorTag = true;
		}

		public String getErrorMessage() {
			return _errorMessage;
		}
	}
}
