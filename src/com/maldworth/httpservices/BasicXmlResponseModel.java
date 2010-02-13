package com.maldworth.httpservices;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.impl.client.BasicResponseHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.maldworth.httpservices.exceptions.HttpServicesException;

public abstract class BasicXmlResponseModel extends DefaultResponseModelBase<String>
{
	private final boolean _isAndroid;
	
	public abstract boolean isError(String unParsedEntity);
	
	public BasicXmlResponseModel(boolean isAndroid)
	{
		super(new BasicResponseHandler());
		
		_isAndroid = isAndroid;
	}
	
	public boolean isAndroid()
	{
		return _isAndroid;
	}
	
	@Override
	public final void parseEntityToObject(String unParsedEntity) throws HttpServicesException
	{
		if(isError(unParsedEntity))
		{
			//TODO handle this, perhaps just do !isError() and handle it in the child class
		}
		else
		{
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp;
			
			try
			{
				sp = spf.newSAXParser();
				
				sp.parse(new InputSource(new StringReader(getUnParsedEntity())),this);
			}
			catch (ParserConfigurationException e)
			{
				e.printStackTrace();
				throw new HttpServicesException("["+e.toString()+"] = Problem with sax xml parsing in "+this.toString());
			}
			catch (SAXException e)
			{
				e.printStackTrace();
				throw new HttpServicesException("["+e.toString()+"] = Problem with sax xml parsing in "+this.toString());
			}
			catch (IOException e)
			{
				e.printStackTrace();
				throw new HttpServicesException("["+e.toString()+"] = Problem with sax xml parsing in "+this.toString());
			}
		}
	}
}
