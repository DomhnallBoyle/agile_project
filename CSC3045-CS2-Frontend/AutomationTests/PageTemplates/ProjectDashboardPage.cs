using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WPFUIItems;
using TestStack.White.UIItems.WindowItems;

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
        public TextBox SearchEmailTextBox
        {
            get { return MainWindow.Get<TextBox>("SearchEmailTextBox"); }
        }
        public Button SearchButton
        {
            get { return MainWindow.Get<Button>("SearchButton"); }
        }
        public Label SearchResultName
        {
            get { return MainWindow.Get<Label>("SearchResultName"); }
        }
        public Button AddToTeamButton
        {
            get { return MainWindow.Get<Button>("AddToTeamButton"); }
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

        public Button GetSetProductOwnerButtonForListItem(WPFListItem listItem)
        {
            return listItem.Get<Button>("SetProductOwnerButton");
        }

        public Button GetSetScrumMasterButtonForListItem(WPFListItem listItem)
        {
            return listItem.Get<Button>("SetScrumMasterButton");
        }

        public Button GetRemoveButtonForScrumMasterListItem(WPFListItem listItem)
        {
            return listItem.Get<Button>("ScrumMasterRemoveButton");
        }
    }
}
