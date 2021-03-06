package org.eclipse.angularjs.internal.ui.viewers;

import org.eclipse.angularjs.internal.ui.ImageResource;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import tern.ITernProject;
import tern.angular.AngularType;
import tern.angular.modules.IAngularElement;
import tern.server.protocol.outline.IJSNode;
import tern.server.protocol.outline.JSNode;

public class AngularElementLabelProvider extends LabelProvider implements ILabelDecorator {

	public static final AngularElementLabelProvider INSTANCE = new AngularElementLabelProvider();

	@Override
	public Image getImage(Object element) {
		if (element instanceof IAngularElement) {
			AngularType type = ((IAngularElement) element).getAngularType();
			if (type != null) {
				switch (type) {
				case module:
					return ImageResource.getImage(ImageResource.IMG_ANGULARJS);
				case controller:
					return ImageResource.getImage(ImageResource.IMG_CONTROLLER);
				case directive:
					return ImageResource.getImage(ImageResource.IMG_DIRECTIVE);
				case filter:
					return ImageResource.getImage(ImageResource.IMG_FILTER);
				case factory:
					return ImageResource.getImage(ImageResource.IMG_FACTORY);
				case provider:
					return ImageResource.getImage(ImageResource.IMG_PROVIDER);
				case service:
					return ImageResource.getImage(ImageResource.IMG_SERVICE);
				default:
					return null;
				}
			}
		}
		if (element instanceof JSNode) {
			JSNode node = (JSNode) element;
			if (node.isProperty()) {
				return ImageResource.getImage(ImageResource.IMG_PROPERTY);
			} else if ("provider.$routeProvider".equals(node.getName())) {
				return ImageResource.getImage(ImageResource.IMG_ROUTE_PROVIDER);
			} else if ("when".equals(node.getKind())) {
				return ImageResource.getImage(ImageResource.IMG_ROUTE_PROVIDER_WHEN);
			}
		}
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof IJSNode) {
			return ((IJSNode) element).getName();
		}
		return super.getText(element);
	}

	@Override
	public Image decorateImage(Image image, Object element) {
		return null;
	}

	@Override
	public String decorateText(String text, Object element) {
		if (element instanceof IJSNode) {
			IJSNode node = ((IJSNode) element);
			ITernProject project = node.getTernProject();
			return node.getName() + " - " + project.getName() + "/" + node.getFile();
		}
		return null;
	}

}
