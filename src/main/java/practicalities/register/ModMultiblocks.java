package practicalities.register;

import practicalities.blocks.multiblock.Multiblock;
import net.minecraft.init.Blocks;

public class ModMultiblocks {
	public static Multiblock test;
	public static Multiblock tesla;
	
	public static void init() {
		Multiblock.init();
		tesla = new Multiblock(
				new String[][] {
						new String[] { " " },
						new String[] { "a" }
				},
				new Object[] {
						'a', Blocks.air
				},
				new String[][] {
						new String[] { " " },
						new String[] { "#" }
				});
		test = new Multiblock(
				new String[][] {
					new String[] {
						" sss ",
						"saaas",
						"wa aw",
						"saaas",
						" sss "
					},
					new String[] {
						"     ",
						" sss ",
						" sss ",
						" sss ",
						"     "
					}
				},
				new Object[] {
					's', Blocks.stone, 'a', Blocks.air, 'w', Blocks.wool, 0
				},
				
				new String[][] {
					new String[] {
						" ### ",
						"#---#",
						"#- -#",
						"#---#",
						" ### "
					},
					new String[] {
						"     ",
						" ### ",
						" ### ",
						" ### ",
						"     "
					}
				}
				).setCenter(2, 0, 2);
	}
}
