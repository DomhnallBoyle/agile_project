using TestStack.White.UIItems;
using TestStack.White.UIItems.ListBoxItems;
using TestStack.White.UIItems.WindowItems;

namespace AutomationTests.PageTemplates
{
    class ProductBacklogPage : BasePage
    {
        public ProductBacklogPage(Window window) : base(window)
        { }

        public override bool IsCurrentPage()
        {
            return PageTitle.Text.Equals("Product Backlog");
        }

        public Button CreateStoryButton
        {
            get { return MainWindow.Get<Button>("CreateStoryButton"); }
        }

        public ListBox StoryListBox
        {
            get { return MainWindow.Get<ListBox>("StoryList"); }
        }

    }
}
