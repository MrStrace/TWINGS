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
	
	public Messages() {
		super("lang"); 
		this.showMessages = getBoolean("show messages");
		this.equip = getString("equip");
		this.unequip = getString("equip");
		this.previewSet = getString("equip");
		this.previewRemoved = getString("equip");
		this.editmodeset = getString("equip");
		
	}
	public Messages init() {
		addDefaults();
		return this;
	}

	
	private void addDefaults() {
		this.addDefault("show messages", true);
		this.addDefault("equip", "%prefix% &9you have equipped %WingName%!");
		this.addDefault("unequip", "%prefix% &9you have unequipped %WingName%!");
		this.addDefault("preview set", "%prefix% &aPreview Location set.");
		this.addDefault("preview removed", "%prefix% &cPreview removed.");
		this.addDefault("editmode", "%prefix% &c%WingName% is now in edit mode.");
	}


	public boolean isShowMessages() {
		return showMessages;
	}


	public String getEquip(Wing wing) {
		return MyColors.format(equip.replace("%prefix%", prefix).replace("%WingName%", wing.getFile().getName().replace(".yml", "")));
	}


	public String getUnequip(Wing wing) {
		return  MyColors.format(unequip.replace("%prefix%", prefix).replace("%WingName%", wing.getFile().getName().replace(".yml", "")));
	}


	public String getPreviewSet(Wing wing) {
		return  MyColors.format(previewSet.replace("%prefix%", prefix).replace("%WingName%", wing.getFile().getName().replace(".yml", "")));
	}


	public String getPreviewRemoved(Wing wing) {
		return  MyColors.format(previewRemoved.replace("%prefix%", prefix).replace("%WingName%", wing.getFile().getName().replace(".yml", "")));
	}


	public String getEditmodeset(Wing wing) {
		return  MyColors.format(editmodeset.replace("%prefix%", prefix).replace("%WingName%", wing.getFile().getName().replace(".yml", "")));
	}
	
}
