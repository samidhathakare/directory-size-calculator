package com.filesystem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileSystemTest {

	private FileSystem fs;

	@BeforeEach
	void setUp() {
		fs = new FileSystem(SeedData.buildTree());
	}

	@Test
	void fileNode_returnsCorrectSize() {
		assertEquals(500, new FileNode("t.txt", 500).getSize());
	}
	@Test
	void fileNode_isNotDirectory() {
		assertFalse(new FileNode("t.txt", 100).isDirectory());
	}
	@Test
	void fileNode_rejectsNegativeSize() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new FileNode("t.txt", -1));
		assertEquals(IllegalArgumentException.class, ex.getClass());
	}

	@Test
	void directoryNode_isDirectory() {
		assertTrue(new DirectoryNode("d") {
		}.isDirectory());
	}
	@Test
	void directoryNode_emptyDirSizeIsZero() {
		assertEquals(0, new DirectoryNode("d") {
		}.getSize());
	}
	@Test
	void directoryNode_sizeIsSumOfChildren() {
		DirectoryNode d = new DirectoryNode("d") {
		};
		d.addChild(new FileNode("a", 100));
		d.addChild(new FileNode("b", 200));
		d.addChild(new FileNode("c", 300));
		assertEquals(600, d.getSize());
	}
	@Test
	void directoryNode_recursiveSizeAcrossLevels() {
		DirectoryNode root = new DirectoryNode("root") {
		};
		root.addChild(new FileNode("f1", 100));
		DirectoryNode sub = new DirectoryNode("sub") {
		};
		sub.addChild(new FileNode("f2", 200));
		sub.addChild(new FileNode("f3", 300));
		root.addChild(sub);
		assertEquals(600, root.getSize());
	}

	@Test
	void cd_movesIntoChild() {
		fs.cd("home");
		assertEquals("home", fs.getCurrentDirName());
	}
	@Test
	void cd_unknownShowsError() {
		assertTrue(fs.cd("nope").contains("no such"));
	}
	@Test
	void cd_fileShowsError() {
		fs.cd("etc");
		assertTrue(fs.cd("hosts").contains("not a directory"));
	}
	@Test
	void cd_dotDot_movesUp() {
		fs.cd("home");
		fs.cd("..");
		assertEquals("/", fs.getCurrentDirName());
	}
	@Test
	void cd_dotDot_atRoot() {
		assertTrue(fs.cd("..").contains("Already at root"));
	}
	@Test
	void cd_slash_jumpsToRoot() {
		fs.cd("home");
		fs.cd("alice");
		fs.cd("/");
		assertEquals("/", fs.getCurrentDirName());
	}

	@Test
	void ls_listsDirContents() {
		String r = fs.ls();
		assertTrue(r.contains("home") && r.contains("etc") && r.contains("var"));
	}
	@Test
	void ls_emptyDirMessage() {
		assertTrue(new FileSystem(new DirectoryNode("e") {
		}).ls().contains("empty"));
	}
	@Test
	void size_outputContainsDirName() {
		fs.cd("etc");
		assertTrue(fs.size().contains("etc"));
	}
	@Test
	void size_etcMatchesExpected() {
		long expected = 1_024 + 4_096 + 8_192;
		DirectoryNode etc = (DirectoryNode) SeedData.buildTree().findChild("etc");
		assertEquals(expected, etc.getSize());
	}
}
