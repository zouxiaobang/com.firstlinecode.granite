package com.firstlinecode.granite.lite.xeps.disco;

import com.firstlinecode.basalt.protocol.core.JabberId;
import com.firstlinecode.basalt.protocol.core.stanza.Iq;
import com.firstlinecode.granite.framework.core.annotations.Dependency;
import com.firstlinecode.granite.framework.core.config.IApplicationConfiguration;
import com.firstlinecode.granite.framework.core.config.IApplicationConfigurationAware;
import com.firstlinecode.granite.framework.processing.IProcessingContext;
import com.firstlinecode.granite.xeps.disco.IDiscoProvider;
import com.firstlinecode.basalt.xeps.disco.DiscoInfo;
import com.firstlinecode.basalt.xeps.disco.DiscoItems;
import com.firstlinecode.basalt.xeps.disco.Feature;
import com.firstlinecode.basalt.xeps.disco.Identity;

public class ImServerDiscoProvider implements IDiscoProvider, IApplicationConfigurationAware {
	@Dependency("standard.im.server.listener")
	private StandardImServerListener imServerListener;
	
	private JabberId serverJid;
	
	@Override
	public DiscoInfo discoInfo(IProcessingContext context, Iq iq, JabberId jid, String node) {
		if (!serverJid.equals(jid) || node != null)
			return null;
		
		DiscoInfo discoInfo = new DiscoInfo();
		
		discoInfo.getFeatures().add(new Feature("http://jabber.org/protocol/disco#info"));
		discoInfo.getFeatures().add(new Feature("http://jabber.org/protocol/disco#items"));
		
		if (imServerListener.isStandardStream()) {
			discoInfo.getFeatures().add(new Feature("jabber:client"));
			discoInfo.getFeatures().add(new Feature("urn:ietf:params:xml:ns:xmpp-streams"));
			discoInfo.getFeatures().add(new Feature("urn:ietf:params:xml:ns:xmpp-stanzas"));
			discoInfo.getFeatures().add(new Feature("urn:ietf:params:xml:ns:xmpp-tls"));
			discoInfo.getFeatures().add(new Feature("urn:ietf:params:xml:ns:xmpp-tls#c2s"));
			discoInfo.getFeatures().add(new Feature("urn:ietf:params:xml:ns:xmpp-sasl"));
			discoInfo.getFeatures().add(new Feature("urn:ietf:params:xml:ns:xmpp-sasl#c2s"));
			discoInfo.getFeatures().add(new Feature("urn:ietf:params:xml:ns:xmpp-bind"));
		}
		
		if (imServerListener.isIMServer()) {
			discoInfo.getIdentities().add(new Identity("server", "im"));
			
			discoInfo.getFeatures().add(new Feature("urn:ietf:params:xml:ns:xmpp-session"));
			discoInfo.getFeatures().add(new Feature("jabber:iq:roster"));
		}
		
		return discoInfo;
	}

	@Override
	public DiscoItems discoItems(IProcessingContext context, Iq iq, JabberId jid, String node) {
		return null;
	}

	@Override
	public void setApplicationConfiguration(IApplicationConfiguration appConfiguration) {
		serverJid = JabberId.parse(appConfiguration.getDomainName());
	}

}
