package com.filesystem;

public class SeedData {
	public static DirectoryNode buildTree() {
		DirectoryNode root = new DirectoryNode("root");
		DirectoryNode home = new DirectoryNode("home");
		root.addChild(home);
		DirectoryNode alice = new DirectoryNode("alice");
		home.addChild(alice);
		alice.addChild(new FileNode("resume.pdf", 204_800));
		alice.addChild(new FileNode("photo.jpg", 1_572_864));
		alice.addChild(new FileNode("notes.txt", 2_048));

		DirectoryNode aliceDocs = new DirectoryNode("documents");
		alice.addChild(aliceDocs);
		aliceDocs.addChild(new FileNode("budget.xlsx", 512_000));
		aliceDocs.addChild(new FileNode("essay.docx", 307_200));

		DirectoryNode bob = new DirectoryNode("bob");
		home.addChild(bob);
		bob.addChild(new FileNode("music.mp3", 5_242_880));
		bob.addChild(new FileNode("video.mp4", 52_428_800));

		DirectoryNode etc = new DirectoryNode("etc");
		root.addChild(etc);
		etc.addChild(new FileNode("hosts", 1_024));
		etc.addChild(new FileNode("passwd", 4_096));
		etc.addChild(new FileNode("config.cfg", 8_192));

		DirectoryNode var = new DirectoryNode("var");
		root.addChild(var);

		DirectoryNode logs = new DirectoryNode("logs");
		var.addChild(logs);
		logs.addChild(new FileNode("system.log", 102_400));
		logs.addChild(new FileNode("error.log", 20_480));
		logs.addChild(new FileNode("access.log", 204_800));

		DirectoryNode cache = new DirectoryNode("cache");
		var.addChild(cache);
		cache.addChild(new FileNode("app.cache", 1_048_576));
		cache.addChild(new FileNode("img.cache", 3_145_728));

		return root;
	}
}