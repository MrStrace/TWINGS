package dev.strace.twings.utils;

import dev.strace.twings.Main;
import dev.strace.twings.utils.objects.TWING;

/**
 * 
 * @author Jason Holweg [STRACE]
 * <b>TWINGS</b><br>
 * Website: <a>https://strace.dev/</a><br>
 * GitHub: <a>https://github.com/MrStrace</a><br>
 * Created: Jun 25, 2021<br>
 *
 */
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
	private String nopermission = "";
	private String nosuchcmd = "";
	private String wingsrecieved = "";
	private String wingsgone = "";
	private String playernotfound = "";
	private String rightclick = "";
	private String leftclick = "";

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
		this.nopermission = getString("noperms");
		this.nosuchcmd = getString("no such command");
		this.wingsrecieved = getString("wings given");
		this.wingsgone = getString("wings gone");
		this.playernotfound = getString("player not found");
		this.rightclick = getString("rightclick to unequip");
		this.leftclick = getString("leftclick to equip");
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
		this.addDefault("list", "%prefix% &7List of all Particles:");
		this.addDefault("bulletpoint", " &7- &f%WingName%");
		this.addDefault("noperms", "%prefix% &cSorry, you don't have the permission to use that.");
		this.addDefault("no such command", "%prefix% &cSorry, there is no such command.");
		this.addDefault("wings given", "%prefix% &7You now have wings for &c%time%&7.");
		this.addDefault("wings gone", "%prefix% &cYour wings are now gone.");
		this.addDefault("player not found", "%prefix% &cthe player wasn't found.");
		this.addDefault("rightclick to unequip", "%prefix% &cRightclick to unequip!");
		this.addDefault("leftclick to equip", "%prefix% &bLeftclick to equip!");
		this.save();
	}

	public boolean isShowMessages() {
		return showMessages;
	}

	public String getEquip(TWING wing) {
		if (wing == null)
			return MyColors.format(equip.replace("%prefix%", prefix));
		return MyColors.format(equip.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName()));
	}

	public String getUnequip(TWING wing) {
		if (wing == null)
			return "";
		return MyColors.format(unequip.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName()));
	}

	public String getPreviewSet(TWING wing) {
		if (wing == null)
			return MyColors.format(previewSet.replace("%prefix%", prefix));
		return MyColors.format(previewSet.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName()));
	}

	public String getPreviewRemoved(TWING wing) {
		if (wing == null)
			return MyColors.format(previewRemoved.replace("%prefix%", prefix));
		return MyColors.format(previewRemoved.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName()));
	}

	public String getEditmodeset(TWING wing) {
		if (wing == null)
			return MyColors.format(editmodeset.replace("%prefix%", prefix));
		return MyColors.format(editmodeset.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName()));
	}

	public String getWingnotfound(String notfound) {
		return MyColors.format(wingnotfound.replace("%prefix%", prefix).replace("%WingName%", notfound));
	}

	public String getEditright(TWING wing) {
		if (wing == null)
			return MyColors.format(editright.replace("%prefix%", prefix));
		return MyColors.format(editright.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName()));
	}

	public String getEditleft(TWING wing) {
		if (wing == null)
			return MyColors.format(editleft.replace("%prefix%", prefix));
		return MyColors.format(editleft.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName()));
	}

	public String getList() {
		return MyColors.format(list.replace("%prefix%", prefix));
	}

	public String getListpoint(TWING wing) {
		return MyColors.format(
				listpoint.replace("%prefix%", prefix).replace("%WingName%", wing.getItemName().replace(" ", "_")));
	}

	public String getNopermission() {
		return MyColors.format(nopermission.replace("%prefix%", prefix));
	}

	public String getNosuchcommand() {
		return MyColors.format(nosuchcmd.replace("%prefix%", prefix));
	}

	public String getWingsrecieved(String time) {
		return MyColors.format(wingsrecieved.replace("%prefix%", prefix).replace("%time%", time));
	}

	public String getWingsgone() {
		return MyColors.format(wingsgone.replace("%prefix%", prefix));
	}
	
	public String getPlayernotfound() {
		return MyColors.format(playernotfound.replace("%prefix%", prefix));
	}
	
	public String getRightclick() {
		return MyColors.format(rightclick.replace("%prefix%", prefix));
	}
	
	public String getLeftclick() {
		return MyColors.format(leftclick.replace("%prefix%", prefix));
	}
}
