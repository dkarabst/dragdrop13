

/*
 * SmartGWT (GWT for SmartClient)
 * Copyright 2008 and beyond, Isomorphic Software, Inc.
 *
 * SmartGWT is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.  SmartGWT is also
 * available under typical commercial license terms - see
 * http://smartclient.com/license
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */

package com.smartgwt.sample.showcase.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.core.Rectangle;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.fields.DataSourceBooleanField;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceEnumField;
import com.smartgwt.client.data.fields.DataSourceFloatField;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.Page;
import com.smartgwt.client.widgets.AnimationCallback;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.CloseClientEvent;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FilterCriteriaFunction;
import com.smartgwt.client.widgets.form.fields.PickTreeItem;
import com.smartgwt.client.widgets.form.fields.SpinnerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.ClickHandler;
import com.smartgwt.client.widgets.form.validator.FloatPrecisionValidator;
import com.smartgwt.client.widgets.form.validator.FloatRangeValidator;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridFieldIfFunction;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellContextClickEvent;
import com.smartgwt.client.widgets.grid.events.CellContextClickHandler;
import com.smartgwt.client.widgets.grid.events.CellSavedEvent;
import com.smartgwt.client.widgets.grid.events.CellSavedHandler;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.events.NodeClickEvent;
import com.smartgwt.client.widgets.tree.events.NodeClickHandler;
import com.smartgwt.client.widgets.viewer.DetailViewer;

public class Showcase implements EntryPoint {
  public void onModuleLoad() {
    RootPanel.get().add(getViewPanel());
  }

  public Canvas getViewPanel() {

    VLayout layout = new VLayout(15);

    layout.addMember(new Label(
        "This is a full-screen example - click the \"Show Example\" button to show fullscreen."));

    final IButton button = new IButton("Show Example");
    button.setWidth(140);
    button.setIcon("silk/layout_content.png");
    button.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
      public void onClick(com.smartgwt.client.widgets.events.ClickEvent event) {
        Rectangle rect = button.getPageRect();
        final Canvas animateOutline = new Canvas();
        animateOutline.setBorder("2px solid black");
        animateOutline.setTop(rect.getTop());
        animateOutline.setLeft(rect.getLeft());
        animateOutline.setWidth(rect.getLeft());
        animateOutline.setHeight(rect.getHeight());

        animateOutline.show();
        animateOutline.animateRect(0, 0, Page.getWidth(), Page.getHeight(),
            new AnimationCallback() {
              public void execute(boolean earlyFinish) {
                animateOutline.hide();
                final FullyScreenApplication appWindow = new FullyScreenApplication();
                appWindow.addCloseClickHandler(new CloseClickHandler() {
                  public void onCloseClick(CloseClientEvent event) {
                    animateOutline.setRect(0, 0, Page.getWidth(), Page.getHeight());
                    animateOutline.show();
                    appWindow.destroy();
                    Rectangle targetRect = button.getPageRect();
                    animateOutline.animateRect(targetRect.getLeft(), targetRect.getTop(),
                        targetRect.getWidth(), targetRect.getHeight(), new AnimationCallback() {
                          public void execute(boolean earlyFinish) {
                            animateOutline.hide();
                          }
                        }, 500);

                  }
                });
                appWindow.show();
              }
            }, 500);
      }
    });

    layout.addMember(button);

    return layout;
  }

  class FullyScreenApplication extends Window {

    FullyScreenApplication() {
      setTitle("Demo Application");
      setHeaderIcon("silk/layout_content.png");
      setWidth100();
      setHeight100();
      setShowMinimizeButton(false);
      setShowCloseButton(true);
      setCanDragReposition(false);
      setCanDragResize(false);
      setShowShadow(false);
      addItem(new ApplicationPanel());
    }
  }

  public String getSourceUrl() {
    return null;
  }

  public String getSourceGenUrl() {
    return null;
  }

}

class ApplicationPanel extends HLayout {

  private SearchForm searchForm;

  private CategoryTreeGrid categoryTree;

  private ItemListGrid itemList;

  private ItemDetailTabPane itemDetailTabPane;

  private Menu itemListMenu;

  public ApplicationPanel() {
    setWidth100();
    setHeight100();
    setLayoutMargin(20);

    DataSource supplyCategoryDS = SupplyCategoryXmlDS.getInstance();
    DataSource supplyItemDS = ItemSupplyXmlDS.getInstance();

    categoryTree = new CategoryTreeGrid(supplyCategoryDS);
    categoryTree.setAutoFetchData(true);
    categoryTree.addNodeClickHandler(new NodeClickHandler() {
      public void onNodeClick(NodeClickEvent event) {
        String category = event.getNode().getAttribute("categoryName");
        findItems(category);
      }
    });

    searchForm = new SearchForm(supplyItemDS);

    // when showing options in the combo-box, only show the options from the
    // selected category if appropriate
    final ComboBoxItem itemNameCB = searchForm.getItemNameField();
    itemNameCB.setPickListFilterCriteriaFunction(new FilterCriteriaFunction() {
      public Criteria getCriteria() {
        ListGridRecord record = categoryTree.getSelectedRecord();
        if ((itemNameCB.getValue() != null) && record != null) {
          Criteria criteria = new Criteria();
          criteria.addCriteria("category", record.getAttribute("categoryName"));
          return criteria;
        }
        return null;
      }
    });

    setupContextMenu();

    itemList = new ItemListGrid(supplyItemDS);
    itemList.addRecordClickHandler(new RecordClickHandler() {
      public void onRecordClick(RecordClickEvent event) {
        itemDetailTabPane.updateDetails();
      }
    });

    itemList.addCellSavedHandler(new CellSavedHandler() {
      public void onCellSaved(CellSavedEvent event) {
        itemDetailTabPane.updateDetails();
      }
    });

    itemList.addCellContextClickHandler(new CellContextClickHandler() {
      public void onCellContextClick(CellContextClickEvent event) {
        itemListMenu.showContextMenu();
        event.cancel();
      }
    });

    SectionStack leftSideLayout = new SectionStack();
    leftSideLayout.setWidth(280);
    leftSideLayout.setShowResizeBar(true);
    leftSideLayout.setVisibilityMode(VisibilityMode.MULTIPLE);
    leftSideLayout.setAnimateSections(true);

    SectionStackSection suppliesCategorySection = new SectionStackSection(
        "Office Supply Categories");
    suppliesCategorySection.setExpanded(true);
    suppliesCategorySection.setItems(categoryTree);

    SectionStackSection instructionsSection = new SectionStackSection("Instructions");
    instructionsSection.setItems(new HelpPane());
    instructionsSection.setExpanded(true);

    leftSideLayout.setSections(suppliesCategorySection, instructionsSection);

    SectionStack rightSideLayout = new SectionStack();
    rightSideLayout.setVisibilityMode(VisibilityMode.MULTIPLE);
    rightSideLayout.setAnimateSections(true);

    searchForm.setHeight(60);
    searchForm.addFindListener(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
      public void onClick(com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
        findItems(null);
      }
    });

    SectionStackSection findSection = new SectionStackSection("Find Items");
    findSection.setItems(searchForm);
    findSection.setExpanded(true);

    SectionStackSection supplyItemsSection = new SectionStackSection("Office Supply Items");
    supplyItemsSection.setItems(itemList);
    supplyItemsSection.setExpanded(true);

    itemDetailTabPane = new ItemDetailTabPane(supplyItemDS, supplyCategoryDS, itemList);
    SectionStackSection itemDetailsSection = new SectionStackSection("Item Details");
    itemDetailsSection.setItems(itemDetailTabPane);
    itemDetailsSection.setExpanded(true);

    rightSideLayout.setSections(findSection, supplyItemsSection, itemDetailsSection);

    addMember(leftSideLayout);
    addMember(rightSideLayout);
  }

  private void setupContextMenu() {
    itemListMenu = new Menu();
    itemListMenu.setCellHeight(22);

    MenuItem detailsMenuItem = new MenuItem("Show Details", "silk/application_form.png");
    detailsMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
      public void onClick(MenuItemClickEvent event) {
        itemDetailTabPane.selectTab(0);
        itemDetailTabPane.updateDetails();
      }
    });

    final MenuItem editMenuItem = new MenuItem("Edit Item", "demoApp/icon_edit.png");
    editMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
      public void onClick(MenuItemClickEvent event) {
        itemDetailTabPane.selectTab(1);
        itemDetailTabPane.updateDetails();
      }
    });

    final MenuItem deleteMenuItem = new MenuItem("Delete Item", "silk/delete.png");
    deleteMenuItem.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {
      public void onClick(MenuItemClickEvent event) {
        itemList.removeSelectedData();
        itemDetailTabPane.clearDetails(null);
      }
    });

    itemListMenu.setData(detailsMenuItem, editMenuItem, deleteMenuItem);
  }

  public void findItems(String categoryName) {

    Criteria findValues;

    String formValue = searchForm.getValueAsString("findInCategory");
    ListGridRecord selectedCategory = categoryTree.getSelectedRecord();
    if (formValue != null && selectedCategory != null) {
      categoryName = selectedCategory.getAttribute("categoryName");
      findValues = searchForm.getValuesAsCriteria();
      findValues.addCriteria("category", categoryName);

    } else if (categoryName == null) {
      findValues = searchForm.getValuesAsCriteria();
    } else {
      findValues = new Criteria();
      findValues.addCriteria("category", categoryName);
    }

    itemList.filterData(findValues);
    itemDetailTabPane.clearDetails(categoryTree.getSelectedRecord());
  }
}

class SearchForm extends DynamicForm {
  private ComboBoxItem itemName;

  private ButtonItem findItem;

  public SearchForm(DataSource supplyItemDS) {

    setDataSource(supplyItemDS);
    setTop(20);
    setCellPadding(6);
    setNumCols(7);
    setStyleName("defaultBorder");

    findItem = new ButtonItem("Find");
    findItem.setIcon("silk/find.png");
    findItem.setWidth(70);
    findItem.setEndRow(false);

    TextItem skuItem = new TextItem("SKU");

    itemName = new ComboBoxItem("itemName");
    itemName.setOptionDataSource(supplyItemDS);
    itemName.setPickListWidth(250);

    CheckboxItem findInCategory = new CheckboxItem("findInCategory");
    findInCategory.setTitle("Use Category");
    findInCategory.setDefaultValue(true);
    findInCategory.setShouldSaveValue(false);

    setItems(findItem, skuItem, itemName, findInCategory);
  }

  public ComboBoxItem getItemNameField() {
    return itemName;
  }

  public void addFindListener(ClickHandler handler) {
    findItem.addClickHandler(handler);
  }

}

class ItemListGrid extends ListGrid {

  public ItemListGrid(DataSource supplyItemDS) {

    setDataSource(supplyItemDS);
    setUseAllDataSourceFields(true);

    ListGridField itemName = new ListGridField("itemName", "Name");
    itemName.setShowHover(true);
    ListGridField unitCost = new ListGridField("unitCost");

    unitCost.setCellFormatter(new CellFormatter() {
      public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
        if (value == null)
          return null;
        try {
          NumberFormat nf = NumberFormat.getFormat("##0.00");
          return "$" + nf.format(((Number) value).floatValue());
        } catch (Exception e) {
          return value.toString();
        }
      }
    });

    SpinnerItem spinnerItem = new SpinnerItem();
    spinnerItem.setStep(0.01);
    unitCost.setEditorType(spinnerItem);

    ListGridField sku = new ListGridField("SKU");
    sku.setCanEdit(false);

    ListGridField description = new ListGridField("description");
    description.setShowHover(true);

    ListGridField category = new ListGridField("category");
    category.setCanEdit(false);

    ListGridField inStock = new ListGridField("inStock");
    inStock.setWidth(55);
    inStock.setAlign(Alignment.CENTER);

    ListGridField nextShipment = new ListGridField("nextShipent");
    nextShipment.setShowIfCondition(new ListGridFieldIfFunction() {
      public boolean execute(ListGrid grid, ListGridField field, int fieldNum) {
        return false;
      }
    });

    setFields(itemName, unitCost, sku, description, category, inStock);
    setCanEdit(true);
    setAlternateRecordStyles(true);
    setCanDragRecordsOut(true);
    setHoverWidth(200);
    setHoverHeight(20);
    setSelectionType(SelectionStyle.SINGLE);
  }
}

class CategoryTreeGrid extends TreeGrid {

  public CategoryTreeGrid(DataSource supplyCategoryDS) {

    setShowHeader(false);
    setLeaveScrollbarGap(false);
    setAnimateFolders(true);
    setCanAcceptDroppedRecords(true);
    setCanReparentNodes(false);
    setSelectionType(SelectionStyle.SINGLE);
    setDataSource(supplyCategoryDS);
  }
}

class HelpPane extends HTMLPane {
  public HelpPane() {
    setContentsURL("data/miniapp/demoApp_helpText.html");
    setOverflow(Overflow.AUTO);
    setStyleName("defaultBorder");
    setPadding(10);
  }
}

class ItemDetailTabPane extends TabSet {

  private DetailViewer itemViewer;

  private DynamicForm editorForm;

  private Label editorLabel;

  private ItemListGrid itemListGrid;

  public ItemDetailTabPane(DataSource supplyItemDS, DataSource supplyCategoryDS,
      ItemListGrid itemListGrid) {

    this.itemListGrid = itemListGrid;
    setStyleName("defaultBorder");
    itemViewer = new DetailViewer();
    itemViewer.setDataSource(supplyItemDS);
    itemViewer.setWidth100();
    itemViewer.setMargin(25);
    itemViewer.setEmptyMessage("Select an item to view its details");

    editorLabel = new Label();
    editorLabel.setWidth100();
    editorLabel.setHeight100();
    editorLabel.setAlign(Alignment.CENTER);
    editorLabel.setContents("Select a record to edit, or a category to insert a new record into");

    editorForm = new DynamicForm();
    editorForm.setWidth(650);
    editorForm.setMargin(25);
    editorForm.setNumCols(4);
    editorForm.setCellPadding(5);
    editorForm.setAutoFocus(false);
    editorForm.setDataSource(supplyItemDS);
    editorForm.setUseAllDataSourceFields(true);

    TextItem sku = new TextItem("SKU", "SKU");
    TextItem description = new TextItem("description");
    description.setWidth(300);
    description.setRowSpan(3);

    PickTreeItem category = new PickTreeItem("category");
    category.setDataSource(supplyCategoryDS);
    category.setEmptyMenuMessage("No Sub Categories");
    category.setCanSelectParentItems(true);

    SpinnerItem unitCost = new SpinnerItem("unitCost");
    unitCost.setStep(0.01);

    CheckboxItem inStock = new CheckboxItem("inStock");

    DateItem nextShipment = new DateItem("nextShipment");
    nextShipment.setUseTextField(true);

    ButtonItem saveButton = new ButtonItem("Save Item");
    saveButton.setAlign(Alignment.CENTER);
    saveButton.setWidth(100);
    saveButton.setColSpan(4);
    saveButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        editorForm.saveData();
      }
    });

    editorForm.setFields(sku, description, category, unitCost, inStock, nextShipment, saveButton);
    editorForm.setColWidths(100, 200, 100, 200);

    Tab viewTab = new Tab("View");
    viewTab.setIcon("silk/application_form.png");
    viewTab.setWidth(70);
    viewTab.setPane(itemViewer);

    Tab editTab = new Tab("Edit");
    editTab.setIcon("demoApp/icon_edit.png");
    editTab.setWidth(70);
    editTab.setPane(editorForm);

    setTabs(viewTab, editTab);

    addTabSelectedHandler(new TabSelectedHandler() {
      public void onTabSelected(TabSelectedEvent event) {
        updateDetails();
      }
    });
  }

  public void clearDetails(Record selectedCategoryRecord) {
    int selectedTab = getSelectedTabNumber();
    if (selectedTab == 0) {
      // view tab : show empty message
      itemViewer.setData((Record[]) null);
    } else {
      // edit tab : show new record editor, or empty message
      if (selectedCategoryRecord != null) {
        updateTab(1, editorForm);
        Map initialValues = new HashMap();
        initialValues.put("category", selectedCategoryRecord.getAttribute("categoryName"));
        editorForm.editNewRecord(initialValues);
      } else {
        updateTab(1, editorLabel);
      }
    }
  }

  public void updateDetails() {
    Record selectedRecord = itemListGrid.getSelectedRecord();
    int selectedTab = getSelectedTabNumber();
    if (selectedTab == 0) {
      // view tab : show empty message
      itemViewer.setData(new Record[] { selectedRecord });
    } else {
      // edit tab : show record editor
      editorForm.editRecord(selectedRecord);
    }
  }
}

class SupplyCategoryXmlDS extends DataSource {

  private static SupplyCategoryXmlDS instance = null;

  public static SupplyCategoryXmlDS getInstance() {
      if (instance == null) {
          instance = new SupplyCategoryXmlDS("supplyCategoryDS");
      }
      return instance;
  }

  public SupplyCategoryXmlDS(String id) {

      setID(id);
      setRecordXPath("/List/supplyCategory");


      DataSourceTextField itemNameField = new DataSourceTextField("categoryName", "Item", 128, true);
      itemNameField.setPrimaryKey(true);

      DataSourceTextField parentField = new DataSourceTextField("parentID", null);
      parentField.setHidden(true);
      parentField.setRequired(true);
      parentField.setRootValue("root");
      parentField.setForeignKey("supplyCategoryDS.categoryName");


      setFields(itemNameField, parentField);

      setDataURL("ds/test_data/supplyCategory.data.xml");
      
      setClientOnly(true);

  }
}
class ItemSupplyXmlDS extends DataSource {

  private static ItemSupplyXmlDS instance = null;

  public static ItemSupplyXmlDS getInstance() {
      if (instance == null) {
          instance = new ItemSupplyXmlDS("supplyItemDS");
      }
      return instance;
  }

  public ItemSupplyXmlDS(String id) {

      setID(id);
      setRecordXPath("/List/supplyItem");
      DataSourceIntegerField pkField = new DataSourceIntegerField("itemID");
      pkField.setHidden(true);
      pkField.setPrimaryKey(true);

      DataSourceTextField itemNameField = new DataSourceTextField("itemName", "Item Name", 128, true);
      DataSourceTextField skuField = new DataSourceTextField("SKU", "SKU", 10, true);

      DataSourceTextField descriptionField = new DataSourceTextField("description", "Description", 2000);
      DataSourceTextField categoryField = new DataSourceTextField("category", "Category", 128, true);
      categoryField.setForeignKey("supplyCategoryDS.categoryName");

      DataSourceEnumField unitsField = new DataSourceEnumField("units", "Units", 5);
      unitsField.setValueMap("Roll", "Ea", "Pkt", "Set", "Tube", "Pad", "Ream", "Tin", "Bag", "Ctn", "Box");

      DataSourceFloatField unitCostField = new DataSourceFloatField("unitCost", "Unit Cost", 5);
      FloatRangeValidator rangeValidator = new FloatRangeValidator();
      rangeValidator.setMin(0);
      rangeValidator.setErrorMessage("Please enter a valid (positive) cost");

      FloatPrecisionValidator precisionValidator = new FloatPrecisionValidator();
      precisionValidator.setPrecision(2);
      precisionValidator.setErrorMessage("The maximum allowed precision is 2");

      unitCostField.setValidators(rangeValidator, precisionValidator);

      DataSourceBooleanField inStockField = new DataSourceBooleanField("inStock", "In Stock");

      DataSourceDateField nextShipmentField = new DataSourceDateField("nextShipment", "Next Shipment");

      setFields(pkField, itemNameField, skuField, descriptionField, categoryField, unitsField,
                unitCostField, inStockField, nextShipmentField);

      setDataURL("ds/test_data/supplyItem.data.xml");
      setClientOnly(true);        
  }
}
