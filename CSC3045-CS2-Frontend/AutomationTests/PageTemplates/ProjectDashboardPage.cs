using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WPFUIItems;
using TestStack.White.UIItems.WindowItems;
using TestStack.White.UIItems.Finders;
using TestStack.White.UIItems.MenuItems;

namespace AutomationTests.PageTemplates
{
    public class ProjectDashboardPage : BasePage
    {
        public Label ProjectManagerNameTextBlock
        {
            get { return MainWindow.Get<Label>("ProjectManagerNameTextBlock"); }
        }

        public Label ProductOwnerNameTextBlock
        {
            get { return MainWindow.Get<Label>("ProductOwnerNameTextBlock"); }
        }

        public Label SearchResultName
        {
            get { return MainWindow.Get<Label>("SearchResultName"); }
        }

        public TextBox SearchEmailTextBox
        {
            get { return MainWindow.Get<TextBox>("SearchEmailTextBox"); }
        }

        public Button SearchButton
        {
            get { return MainWindow.Get<Button>("SearchButton"); }
        }

        public Button AddToTeamButton
        {
            get { return MainWindow.Get<Button>("AddToTeamButton"); }
        }

        public Button UserDashboardButton
        {
            get { return MainWindow.Get<Button>("UserDashboardButton"); }
        }

        public Button ProductBacklogButton
        {
            get { return MainWindow.Get<Button>("ProductBacklogButton"); }
        }

        public Button ManageSprintsButton
        {
            get { return MainWindow.Get<Button>("ManageSprintsButton"); }
        }

        public ListBox TeamMembersListBox
        {
            get { return MainWindow.Get<ListBox>("TeamMembersListBox"); }
        }

        public ListBox ScrumMastersListBox
        {
            get { return MainWindow.Get<ListBox>("ScrumMastersListBox"); }
        }

        public ProjectDashboardPage(Window window) : base(window) { }

        public override bool IsCurrentPage()
        {
            return PageTitle.Text.Equals("Project Dashboard");
        }

        public WPFListItem GetTeamMemberListItem(string fullName)
        {
            try
            {
                TeamMembersListBox.Select(fullName);
                return (WPFListItem)TeamMembersListBox.SelectedItem;
            }
            catch (UIActionException ex)
            {
                return null;
            }
        }
        public WPFListItem GetScrumMasterListItem(string fullName)
        {
            try
            {
                ScrumMastersListBox.Select(fullName);
                return (WPFListItem)ScrumMastersListBox.SelectedItem;
            }
            catch (UIActionException ex)
            {
                return null;
            }
        }

        public Menu GetSetProductOwnerContextMenuItem(Window window)
        {
            return window.Popup.ItemBy(SearchCriteria.ByAutomationId("SetAsProductOwnerMenuOption"));
        }
        
        public Menu GetSetScrumMasterContextMenuItem(Window window)
        {
            return window.Popup.ItemBy(SearchCriteria.ByAutomationId("SetAsScrumMasterMenuOption"));
        }

        public Menu GetRemoveScrumMasterContextMenuItem(Window window)
        {
            return window.Popup.ItemBy(SearchCriteria.ByAutomationId("RemoveScrumMasterMenuOption"));
        }
    }
}
