      _app = app;
   }


	protected void paintComponent(Graphics g)
	{
		setPreferredSize(getRequiredSize());
		super.paintComponent(g);
	}

	public void remove(Component comp)
	{
		if (comp != null)
		{
			comp.removeComponentListener(_listener);
         super.remove(comp);
		}
		revalidate();
		repaint();
	}

	protected void addImpl(Component comp, Object constraints, int index)
	{
		if (comp != null)
		{
			comp.addComponentListener(_listener);
			revalidate();
		}
		super.addImpl(comp, constraints, index);
	}
