using TestStack.White.UIItems;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests.PageTemplates
{
    public class ProjectDashboardPage : BasePage
    {
        public Label ProjectManagerNameTextBlock
        {
            get { return MainWindow.Get<Label>("ProjectManagerNameTextBlock"); }
        }

        public ProjectDashboardPage(Window window) : base(window) { }

        public override bool IsCurrentPage()
        {
            return PageTitle.Text.Equals("Project Dashboard");
        }

        public Button CreateProjectButton
        {
            get { return MainWindow.Get<Button>("CreateProjectButton"); }
        }

        public Button ProductBacklogButton
        {
            get { return MainWindow.Get<Button>("ProductBacklogButton"); }
        }

  
    }
}
