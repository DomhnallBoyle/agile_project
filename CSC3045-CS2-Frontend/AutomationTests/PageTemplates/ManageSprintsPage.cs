using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests.PageTemplates
{
    public class ManageSprintsPage : BasePage
    {
        public Label ProjectNameTextBlock
        {
            get { return MainWindow.Get<Label>("ProjectNameTextBlock"); }
        }

        public ListBox SprintListBox
        {
            get { return MainWindow.Get<ListBox>("SprintListBox"); }
        }

        public Button BackButton
        {
            get { return MainWindow.Get<Button>("BackButton"); }
        }

        public Button CreateSprintButton
        {
            get { return MainWindow.Get<Button>("CreateSprintButton"); }
        }

        public ManageSprintsPage(Window window) : base(window) { }

        public override bool IsCurrentPage()
        {
            return PageTitle.Text.Equals("Manage Sprints");
        }

        public WPFListItem GetSprintListItem(string sprintName)
        {
            try
            {
                SprintListBox.Select(sprintName);
                return (WPFListItem)SprintListBox.SelectedItem;
            }
            catch (UIActionException ex)
            {
                return null;
            }
        }
    }
}
