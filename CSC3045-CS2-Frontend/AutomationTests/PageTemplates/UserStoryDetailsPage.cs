using AutomationTests.Util;
using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WindowItems;


namespace AutomationTests.PageTemplates
{
    class UserStoryDetailsPage : BasePage
    {
        public UserStoryDetailsPage(Window window) : base(window)
        { }

        public override bool IsCurrentPage()
        {
            return PageTitle.Text.Equals("");
        }

        public Button CreateAcceptanceTestButton
        {
            get { return MainWindow.Get<Button>("CreateAcceptanceTestButton"); }
        }

        public ListBox UserStoryAcceptanceTests
        {
            get { return MainWindow.Get<ListBox>("UserStoryAcceptanceTests"); }
        }

    }

    
}
