package com.github.telvarost.clientsideessentials;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.GameOptions;

public class PostProcess {
	public static final float DEFAULT_GAMMA = 2.2F;
	public static final double DEFAULT_GAMMA_D = (double)DEFAULT_GAMMA;
	public static final double DEFAULT_GAMMA_INV = 1.0D / DEFAULT_GAMMA_D;
	public static final float MAX_GAMMA = 3.4F;
	public static final float MIN_GAMMA = 1.0F;
	public static final float GAMMA_RANGE = MAX_GAMMA - MIN_GAMMA;
	public boolean anaglyph3d = false;
	private double gamma = DEFAULT_GAMMA_D;
	public static PostProcess instance = new PostProcess();

	public PostProcess() {
		this.load(new File(Minecraft.getGameDirectory(), "options.txt"));
	}

	private float getCalcGamma() {
		return (ModOptions.gamma * 5F);
		//return (ModOptions.gamma * 5F); // 0.0F and 5.0F currently
	}

	public void set(GameOptions options) {
		this.anaglyph3d = options.anaglyph3d;
		//ModOptions.gamma = (double)ModOptions.gamma;
	}

	public void load(File optionsFile) {
		try {
			if(!optionsFile.exists()) {
				return;
			}

			BufferedReader reader = new BufferedReader(new FileReader(optionsFile));
			String line = "";

			while((line = reader.readLine()) != null) {
				try {
					String[] split = line.split(":");
					if(split[0].equals("postprocessGamma")) {
						ModOptions.gamma = this.readFloat(split[1]);
					}

					if(split[0].equals("anaglyph3d")) {
						split[1].equals("true");
					}
				} catch (Exception e1) {
					System.out.println("Skipping bad option: " + line);
				}
			}

			reader.close();
		} catch (Exception e) {
			System.out.println("Failed to load options");
			e.printStackTrace();
		}

	}

	private float readFloat(String s) {
		return s.equals("true") ? 1.0F : (s.equals("false") ? 0.0F : Float.parseFloat(s));
	}

	public int red(int r, int g, int b) {
		if(getCalcGamma() != DEFAULT_GAMMA_D) {
			r = (int)(Math.pow(Math.pow((double)r / 255.0D, getCalcGamma()), DEFAULT_GAMMA_INV) * 255.0D);
		}

		if(this.anaglyph3d) {
			r = (r * 30 + g * 59 + b * 11) / 100;
		}

		return r;
	}

	public int green(int r, int g, int b) {
		if(getCalcGamma() != DEFAULT_GAMMA_D) {
			g = (int)(Math.pow(Math.pow((double)g / 255.0D, getCalcGamma()), DEFAULT_GAMMA_INV) * 255.0D);
		}

		if(this.anaglyph3d) {
			g = (r * 30 + g * 70) / 100;
		}

		return g;
	}

	public int blue(int r, int g, int b) {
		if(getCalcGamma() != DEFAULT_GAMMA_D) {
			b = (int)(Math.pow(Math.pow((double)b / 255.0D, getCalcGamma()), DEFAULT_GAMMA_INV) * 255.0D);
		}

		if(this.anaglyph3d) {
			b = (r * 30 + b * 70) / 100;
		}

		return b;
	}

	public float red(float r, float g, float b) {
		if (r != 0) {
			if(getCalcGamma() != DEFAULT_GAMMA_D) {
				r = (float)Math.pow(Math.pow((double)r, getCalcGamma()), DEFAULT_GAMMA_INV);
			}

			if(this.anaglyph3d) {
				r = (r * 30.0F + g * 59.0F + b * 11.0F) / 100.0F;
			}
		}

		return r;
	}

	public float green(float r, float g, float b) {
		if (g != 0) {
			if (getCalcGamma() != DEFAULT_GAMMA_D) {
				g = (float) Math.pow(Math.pow((double) g, getCalcGamma()), DEFAULT_GAMMA_INV);
			}

			if (this.anaglyph3d) {
				g = (r * 30.0F + g * 70.0F) / 100.0F;
			}
		}

		return g;
	}

	public float blue(float r, float g, float b) {
		if (b != 0) {
			if (getCalcGamma() != DEFAULT_GAMMA_D) {
				b = (float) Math.pow(Math.pow((double) b, getCalcGamma()), DEFAULT_GAMMA_INV);
			}

			if (this.anaglyph3d) {
				b = (r * 30.0F + b * 70.0F) / 100.0F;
			}
		}

		return b;
	}
}