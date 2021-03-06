package com.firstlinecode.granite.xeps.disco;

import com.firstlinecode.basalt.protocol.core.stanza.Iq;
import com.firstlinecode.granite.framework.core.annotations.Dependency;
import com.firstlinecode.granite.framework.processing.IProcessingContext;
import com.firstlinecode.granite.framework.processing.IXepProcessor;
import com.firstlinecode.basalt.xeps.disco.DiscoInfo;

public class DiscoInfoProcessor implements IXepProcessor<Iq, DiscoInfo> {
	@Dependency("disco.processor")
	private IDiscoProcessor discoProcessor;

	@Override
	public void process(IProcessingContext context, Iq iq, DiscoInfo discoInfo) {
		discoProcessor.discoInfo(context, iq, iq.getTo() == null ? context.getJid() : iq.getTo(), discoInfo.getNode()); 
	}

}
