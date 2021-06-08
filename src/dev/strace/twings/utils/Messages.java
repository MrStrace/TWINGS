package dev.strace.twings.utils;

import dev.strace.twings.Main;
import dev.strace.twings.utils.objects.Wing;

public class Messages extends ConfigManager {

	private boolean showMessages = true;
	private String equip = "";
	private String unequip = "";
	private String previewSet = "";
	private String previewRemoved = "";
	private String editmodeset = "";
	private String prefix = Main.getInstance().getPrefix();
	private String wingnotfound = "";
	private String editright = "";
	private String editleft = "";
	private String list = "";
	private String listpoint = "";
	
	public Messages() {
		super("lang"); 
		init();

	}
	public Messages init() {
		addDefaults();
		return this;
	}
	public Messages load() {
		this.showMessages = getBoolean("show messages");
		this.equip = getString("equip");
		this.unequip = getString("unequip");
		this.previewSet = getString("preview set");
		this.previewRemoved = getString("preview removed");
		this.editmodeset = getString("editmode");
		this.wingnotfound = getString("wing not found");
		this.editright = getString("menu.editright");
		this.editleft = getString("menu.editleft");
		this.list = getString("list");
		this.listpoint = getString("bulletpoint");
		return this;
	}

	
	private void addDefaults() {
		this.addDefault("show messages", true);
		this.addDefault("equip", "%prefix% &9you have equipped %WingName%!");
		this.addDefault("unequip", "%prefix% &9you have unequipped %WingName%!");
		this.addDefault("preview set", "%prefix% &aPreview Location set.");
		this.addDefault("preview removed", "%prefix% &cPreview removed.");
		this.addDefault("editmode", "%prefix% &c%WingName% is now in edit mode.");
		this.addDefault("wing not found", "%prefix% &c%WingName% not found. All Particles: &d/twings list");
		this.addDefault("menu.editleft", "&cLeft click to set the edit status");
		this.addDefault("menu.editright", "&cRight click to remove the edit status");
		this.addDefault("list", "%prefix % &7List of all Particles:");
		this.addDefault("bulletpoint", " &7- &f%WingName%");
		this.save();
	}


	public boolean isShowMessages() {
		return showMessages;
	}


	public String getEquip(Wing wing) {
		if(wing == null) return MyColors.format(equip.replace("%prefix%", prefix));
		return MyColors.format(equip.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName()));
	}


	public String getUnequip(Wing wing) {
		if(wing == null) return "";
		return  MyColors.format(unequip.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName()));
	}


	public String getPreviewSet(Wing wing) {
		if(wing == null) return MyColors.format(previewSet.replace("%prefix%", prefix));
		return  MyColors.format(previewSet.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName()));
	}


	public String getPreviewRemoved(Wing wing) {
		if(wing == null) return MyColors.format(previewRemoved.replace("%prefix%", prefix));
		return  MyColors.format(previewRemoved.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName()));
	}


	public String getEditmodeset(Wing wing) {
		if(wing == null) return MyColors.format(editmodeset.replace("%prefix%", prefix));
		return  MyColors.format(editmodeset.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName()));
	}
	
	public String getWingnotfound(String notfound) {
		return  MyColors.format(wingnotfound.replace("%prefix%", prefix).replace("%WingName%", notfound));
	}
	
	public String getEditright(Wing wing) {
		if(wing == null) return MyColors.format(editright.replace("%prefix%", prefix));
		return  MyColors.format(editright.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName()));
	}
	
	public String getEditleft(Wing wing) {
		if(wing == null) return MyColors.format(editleft.replace("%prefix%", prefix));
		return  MyColors.format(editleft.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName()));
	}
	
	public String getList() {
		return  MyColors.format(list.replace("%prefix%", prefix));
	}
	
	public String getListpoint(Wing wing) {
		return  MyColors.format(listpoint.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName().replace(" ", "_")));
	}
}
